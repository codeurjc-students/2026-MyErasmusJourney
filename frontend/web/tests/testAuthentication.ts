import { createApiClient } from "@shared/api/apiClient";
import type { LoginRequest } from "@shared/models/LoginRequest";
import type { UserFormDTO } from "@shared/models/UserFormDTO";
import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";
import { createAuthService } from "@shared/services/auth.service";
import { createUserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import { APIURL } from "src/config/env";

const testAPI = createApiClient(APIURL);
const testAuthService = createAuthService(testAPI);
const testUserService = createUserService(testAPI);

const setUser = useUserStore.getState().setUser;

export async function authenticateUser(email: string): Promise<UserSimpleDTO> {
    const loginRequest: LoginRequest = {
        username: email,
        password: "password",
    };

    const user = await obtainAuthenticatedUser(loginRequest);
    setUser(user);

    return user;
}

async function obtainAuthenticatedUser(loginRequest: LoginRequest): Promise<UserSimpleDTO> {
    await testAuthService.logIn(loginRequest);

    const user = await testUserService.getUserInfo();

    return {
        id: user.id,
        displayName: user.displayName,
        email: user.email,
        userImageId: user.userImageId,
    };
}

export function clearUserStore() {
    testAuthService.logOut();
    setUser(null);
}

export async function authenticateUserToDelete(){
    clearUserStore();

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