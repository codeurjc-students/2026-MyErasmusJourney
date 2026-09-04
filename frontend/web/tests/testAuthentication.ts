import { createApiClient } from "@shared/api/apiClient";
import type { LoginRequest } from "@shared/models/LoginRequest";
import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";
import { createAuthService } from "@shared/services/auth.service";
import { createUserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import { APIURL } from "src/config/env";
import makeFetchCookie from "fetch-cookie";
import type { UserFormDTO } from "@shared/models/UserFormDTO";

const originalFetch = globalThis.fetch;

const testAPI = createApiClient(APIURL);
const testAuthService = createAuthService(testAPI);
const testUserService = createUserService(testAPI);

const setUser = useUserStore.getState().setUser;

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
        password: "password123"
    };

    const user = await obtainAuthenticatedUser(loginRequest);

    setUser(user);

    return user;
}

export async function authenticateUser(
    email: string
): Promise<UserSimpleDTO> {

    setupFetchWithCookies();

    const loginRequest: LoginRequest = {
        username: email,
        password: "password"
    };

    const user = await obtainAuthenticatedUser(loginRequest);

    setUser(user);

    return user;
}

function setupFetchWithCookies() {
    // IMPORTANTE:
    // Siempre partimos del fetch original, nunca de globalThis.fetch.
    const cookieAwareFetch = makeFetchCookie(originalFetch);

    globalThis.fetch = cookieAwareFetch as typeof fetch;
}

async function obtainAuthenticatedUser(
    loginRequest: LoginRequest
): Promise<UserSimpleDTO> {

    await testAuthService.logIn(loginRequest);

    try {
        const user = await testUserService.getUserInfo();

        return {
            id: user.id,
            displayName: user.displayName,
            email: user.email
        };

    } catch (error) {
        console.log("Error authenticating:", loginRequest);
        console.error(error);

        return {
            id: 0,
            displayName: "",
            email: ""
        };
    }
}

export function clearFetchAndUserStore() {
    globalThis.fetch = originalFetch;
    setUser(null);
}