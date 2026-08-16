import { createApiClient } from "@shared/apiClient";
import type { LoginRequest } from "@shared/models/LoginRequest";
import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";
import { createAuthService } from "@shared/services/auth.service";
import { createUserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import { APIURL } from "src/config/env";
import makeFetchCookie from "fetch-cookie";


const originalFetch = globalThis.fetch;

const testAPI = createApiClient(APIURL);
const testAuthService = createAuthService(testAPI);
const testUserService = createUserService(testAPI);

const setUser = useUserStore.getState().setUser;

export async function authenticateUser(admin: boolean): Promise<UserSimpleDTO> {

    setupFetchWithCookies();

    const loginRequest: LoginRequest = admin
        ? {
            username: "testadmin@email.com",
            password: "password"
        }
        : {
            username: "test@email.com",
            password: "password"
        };

    const user = await obtainAuthenticatedUser(loginRequest);

    setUser(user);

    return user;
}

function setupFetchWithCookies() {
    const cookieAwareFetch = makeFetchCookie(globalThis.fetch);
    globalThis.fetch = cookieAwareFetch as typeof fetch;
}

async function obtainAuthenticatedUser(
    loginRequest: LoginRequest
): Promise<UserSimpleDTO> {

    await testAuthService.logIn(loginRequest);

    const user = await testUserService.getUserInfo();

    return {
        id: user.id,
        displayName: user.displayName,
        email: user.email
    };
}

export function clearFetchAndUserStore() {
    globalThis.fetch = originalFetch;
    useUserStore.getState().setUser(null);
}