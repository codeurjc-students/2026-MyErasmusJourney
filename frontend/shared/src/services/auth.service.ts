import type { ApiClient } from "../api/apiClient";
import type { LoginRequest } from "../models/LoginRequest";
import { ApiError } from "../api/apiError";

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
      throw new ApiError(response.status, await response.text());
    }

    const data =  await response.json();

    if(data.status !== "SUCCESS"){
        throw new ApiError(400, "Bad credentials");
    }

    return data;
}

async function logOut(api:ApiClient){
  const response = await api.post("/auth/logout", null);

    if (!response.ok){
      throw new ApiError(response.status, await response.text());
    }

    return response.json();
}