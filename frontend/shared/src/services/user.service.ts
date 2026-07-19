import type { ApiClient } from "../apiClient";
import type { UserFormDTO } from "../models/UserFormDTO";

export type UserService = ReturnType<typeof createUserService>;

export function createUserService(api:ApiClient) {
  return {
    signUp: (body: UserFormDTO) => signUp(api, body)
  };
}

async function signUp(api: ApiClient, body:UserFormDTO){
    const response = await api.post("/experiences/", body)

    if (!response.ok){
        throw new Error(response.body?.toString() || "Error signing up");
    }

    return response.json();
}