import type { ApiClient } from "../apiClient";
import type { UserFormDTO } from "../models/UserFormDTO";

export type UserService = ReturnType<typeof createUserService>;

export function createUserService(api:ApiClient) {
  return {
    signUp: (body: UserFormDTO) => signUp(api, body),
    getUserInfo: () => getUserInfo(api),
    getUserById: (id: number) => getUserById(api, id),
    deleteUserById: (id:number) => deleteUserById(api, id),
    getExperiences: (id:number) => getExperiences(api, id)
  };
}

async function signUp(api: ApiClient, body:UserFormDTO){
    const response = await api.post("/users/", body)

    if (!response.ok){
      const error = await response.text();
      throw new Error(error);
    }

    return response.json();
}

async function getUserInfo(api:ApiClient) {
  const response = await api.get("/users/me")

    if (!response.ok) {
      const error = await response.text();
      throw new Error(error);
    }

    return  response.json();
}

async function getUserById(api:ApiClient, id:number){

  const response = await api.get(`/users/${id}`)

    if (!response.ok){
      const error = await response.text();
      throw new Error(error);
    }

    return response.json();
}

async function deleteUserById(api: ApiClient, id:number){

  const response = await api.delete(`/users/${id}`);

  if (!response.ok){
      const error = await response.text();
      throw new Error(error);
    }

  return response.json();
}

async function getExperiences(api: ApiClient, id:number){
  const response = await api.get(`/users/${id}/experiences`);

  if (!response.ok){
      const error = await response.text();
      throw new Error(error);
    }

  return response.json();
}