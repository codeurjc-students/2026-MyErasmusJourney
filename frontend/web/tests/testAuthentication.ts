/**
 * Test helpers for authenticating against the real backend in integration tests.
 *
 * Two problems are solved here:
 *
 * 1. Session cookies: Node's fetch has no cookie jar (browsers do), so a
 *    cookie-aware fetch is installed globally. After logging in, every request
 *    made by the app and the services carries the session cookie.
 *
 * 2. Multipart uploads: under jsdom, FormData/File come from jsdom while fetch
 *    comes from Node (undici). Undici doesn't recognize jsdom's FormData and
 *    sends it as the string "[object FormData]" (text/plain). For requests to
 *    "/multimedia" the multipart body is therefore built manually as a Buffer.
 */

import { createApiClient } from "@shared/api/apiClient";
import type { LoginRequest } from "@shared/models/LoginRequest";
import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";
import { createAuthService } from "@shared/services/auth.service";
import { createUserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import { APIURL } from "src/config/env";
import makeFetchCookie from "fetch-cookie";
import { CookieJar } from "tough-cookie";
import type { UserFormDTO } from "@shared/models/UserFormDTO";
import { randomUUID } from "node:crypto";

// Node's native fetch, saved before we replace globalThis.fetch so it can be restored later
const originalFetch = globalThis.fetch;

// API client and services used only for the login/signup calls made by this helper
const testAPI = createApiClient(APIURL);
const testAuthService = createAuthService(testAPI);
const testUserService = createUserService(testAPI);

// Stores the session cookie received on login
const cookieJar = new CookieJar();

// Wraps the native fetch so it saves and sends cookies through the jar
const cookieAwareFetch = makeFetchCookie(
    originalFetch,
    cookieJar
);

const setUser = useUserStore.getState().setUser;

/**
 * Creates a throwaway user (sign-up) and logs in as that user.
 * Meant for tests that delete the account, so shared test users aren't affected.
 * If the sign-up fails (e.g. the user already exists from a previous run) the
 * error is logged and the login is attempted anyway.
 *
 * @returns the authenticated user, also saved in the user store
 */
export async function authenticateUserToDelete(): Promise<UserSimpleDTO> {
    setupFetchWithCookies();

    const signUpForm: UserFormDTO = {
        fullName: "John Doe",
        displayName: "johndoe",
        email: "usertodelete@example.com",
        city: null,
        country: null,
        password: "password123",
        passwordConfirmation: "password123",
    };

    try {
        await testUserService.signUp(signUpForm);
    } catch (error) {
        console.error(error);
    }

    const loginRequest: LoginRequest = {
        username: "usertodelete@example.com",
        password: "password123",
    };

    const user = await obtainAuthenticatedUser(loginRequest);

    setUser(user);

    return user;
}

/**
 * Logs in as an existing test user (all of them share the password "password")
 * and stores the user in the user store.
 *
 * @param email email of an existing user in the test database
 * @returns the authenticated user, also saved in the user store
 */
export async function authenticateUser(
    email: string
): Promise<UserSimpleDTO> {
    setupFetchWithCookies();

    const loginRequest: LoginRequest = {
        username: email,
        password: "password",
    };

    const user = await obtainAuthenticatedUser(loginRequest);

    setUser(user);

    return user;
}

/**
 * Replaces the global fetch with testFetch so that every request made
 * during the test goes through the cookie handling and multipart workaround.
 */
function setupFetchWithCookies() {
    globalThis.fetch = testFetch;
}

/**
 * Reads a Blob/File into a Node Buffer using jsdom's FileReader.
 * FileReader is used (instead of blob.arrayBuffer()) because it works reliably
 * with jsdom's own File implementation.
 *
 * @param file the file or blob to read
 * @returns the full binary content of the file
 */
function readAsBuffer(file: Blob): Promise<Buffer> {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => resolve(Buffer.from(reader.result as ArrayBuffer));
        reader.onerror = () => reject(reader.error);
        reader.readAsArrayBuffer(file);
    });
}

/**
 * Manually serializes a FormData into a multipart/form-data body.
 * Text fields become simple parts; files become parts with a filename and
 * content type followed by their raw bytes. Done by hand because Node's fetch
 * can't serialize jsdom's FormData (see the header of this file).
 *
 * Note: filenames are not escaped, so they must not contain quotes.
 *
 * @param source the FormData built by the component/service
 * @returns the body as a Buffer and the Content-Type header (with the boundary)
 */
async function toMultipart(source: FormData): Promise<{ body: Buffer; contentType: string }> {
    // Random separator between parts; it must not appear inside the content
    const boundary = `----vitest${randomUUID().replace(/-/g, "")}`;
    const chunks: Buffer[] = [];

    for (const [key, value] of source.entries()) {
        if (typeof value === "string") {
            // Text field
            chunks.push(Buffer.from(
                `--${boundary}\r\n` +
                `Content-Disposition: form-data; name="${key}"\r\n\r\n` +
                `${value}\r\n`
            ));
        } else {
            // File field: part headers, then raw bytes, then line break
            chunks.push(Buffer.from(
                `--${boundary}\r\n` +
                `Content-Disposition: form-data; name="${key}"; filename="${value.name}"\r\n` +
                `Content-Type: ${value.type || "application/octet-stream"}\r\n\r\n`
            ));
            chunks.push(await readAsBuffer(value));
            chunks.push(Buffer.from("\r\n"));
        }
    }

    // Closing boundary marks the end of the body
    chunks.push(Buffer.from(`--${boundary}--\r\n`));

    return {
        body: Buffer.concat(chunks),
        contentType: `multipart/form-data; boundary=${boundary}`,
    };
}

/**
 * Fetch implementation installed globally during tests.
 * - Requests to "/multimedia": the FormData body is converted to a manual
 *   multipart Buffer and the cookie is attached by hand.
 * - Any other request: goes through the cookie-aware fetch, which handles
 *   cookies automatically.
 */
async function testFetch(
    input: RequestInfo | URL,
    init?: RequestInit
): Promise<Response> {
    const url = input.toString();

    if (url.includes("/multimedia")) {
        const cookie = await cookieJar.getCookieString(APIURL);

        let body = init?.body as any;
        const headers: Record<string, string> = {
            ...(init?.headers as Record<string, string>),
            Cookie: cookie,
        };

        if (body instanceof FormData) {
            const multipart = await toMultipart(body);
            body = multipart.body;
            // Sets the Content-Type with the boundary used in the body
            headers["Content-Type"] = multipart.contentType;
        }

        return originalFetch(input, { ...init, body, headers });
    }

    return cookieAwareFetch(input, init);
}

/**
 * Logs in with the given credentials (the session cookie is stored in the jar)
 * and then fetches the current user's info from the backend.
 * If fetching the user info fails, the error is logged and an empty user
 * (id 0) is returned, so check for that if a test seems unauthenticated.
 *
 * @param loginRequest username (email) and password
 * @returns the simplified user data
 */
async function obtainAuthenticatedUser(
    loginRequest: LoginRequest
): Promise<UserSimpleDTO> {

    await testAuthService.logIn(loginRequest);

    try {
        const user = await testUserService.getUserInfo();

        return {
            id: user.id,
            displayName: user.displayName,
            email: user.email,
        };
    } catch (error) {
        console.error(error);

        return {
            id: 0,
            displayName: "",
            email: "",
        };
    }
}

/**
 * Cleanup for afterAll/afterEach: restores the original fetch and clears the
 * user from the store so state doesn't leak between test files.
 */
export function clearFetchAndUserStore() {
    globalThis.fetch = originalFetch;
    setUser(null);
}
