import { createApiClient } from "@shared/apiClient";
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

export async function authenticateUserToDelete(): Promise<UserSimpleDTO>{
    
    setupFetchWithCookies();

    const signUpForm: UserFormDTO = {
        fullName: "John Doe",
        displayName: "johndoe",
        email: "john@example.com",
        city:null,
        country:null,
        password: "password123",
        passwordConfirmation: "password123",
    }

    try{
        await testUserService.signUp(signUpForm);
    } catch(error){
        console.error(error);
    }
    const loginRequest: LoginRequest = {username: "john@example.com", password: "password123"}

    const user = await obtainAuthenticatedUser(loginRequest);

    setUser(user);

    return user;
}

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