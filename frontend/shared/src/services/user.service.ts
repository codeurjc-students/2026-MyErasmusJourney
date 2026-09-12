import { ApiError } from "../api/apiError";
import type { ApiClient } from "../api/apiClient";
import type { UserFormDTO } from "../models/UserFormDTO";
import type { UserDTO } from "@shared/models/UserDTO";

export type UserService = ReturnType<typeof createUserService>;

export function createUserService(api: ApiClient) {
  return {
    signUp: (body: UserFormDTO) => signUp(api, body),
    getUserInfo: () => getUserInfo(api),
    getUserById: (id: number) => getUserById(api, id),
    deleteUserById: (id: number) => deleteUserById(api, id),
    getExperiences: (id: number) => getExperiences(api, id),
    getComments: (id: number) => getComments(api, id),
    updateUser: (id: number, body:UserDTO) => updateUser(api, id, body)
  };
}

async function signUp(api: ApiClient, body: UserFormDTO) {
  const response = await api.post("/users/", body)

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());

  }

  return response.json();
}

async function getUserInfo(api: ApiClient) {
  const response = await api.get("/users/me")

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());

  }

  return response.json();
}

async function getUserById(api: ApiClient, id: number) {

  const response = await api.get(`/users/${id}`)

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());

  }

  return response.json();
}

async function deleteUserById(api: ApiClient, id: number) {

  const response = await api.delete(`/users/${id}`);

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());

  }

  return response.json();
}

async function getExperiences(api: ApiClient, id: number) {
  const response = await api.get(`/users/${id}/experiences`);

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());

  }

  return response.json();
}

async function getComments(api: ApiClient, id: number) {
  const response = await api.get(`/users/${id}/comments`);

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());
  }

  return response.json();
}

async function updateUser(api: ApiClient, id:number,  body: UserDTO) {
  const response = await api.put(`/users/${id}`, body);

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());

  }

  return response.json();
}