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

    const data =  await response.json();
    
    if (!response.ok || data.status !== "SUCCESS"){
        throw new Error("Error loggin in");
    }

    return data;
}

async function logOut(api:ApiClient){
  const response = await api.post("/auth/logout", null);

    if (!response.ok){
      throw new Error("Error loggin out");
    }

    return response.json();
}