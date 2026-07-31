import type { ApiClient } from "../apiClient";
import type { LoginRequest } from "../models/LoginRequest";

export type AuthService = ReturnType<typeof createAuthService>;

export function createAuthService(api:ApiClient) {
  return {
    logIn: (body:LoginRequest) => logIn(api, body),
    logOut: () => logOut(api)
  };
}

async function logIn(api: ApiClient, body: LoginRequest){
    const response = await api.post("/auth/login", body);

    if (!response.ok){
        throw new Error("Error loggin in");
    }

    return response.json();
}

async function logOut(api:ApiClient){
  const response = await api.get("/auth/logout");

    if (!response.ok){
        throw new Error("Error loggin in");
    }

    return response.json();
}