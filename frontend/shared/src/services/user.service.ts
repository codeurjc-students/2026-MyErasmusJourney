import { ApiError } from "../api/apiError";
import type { ApiClient } from "../api/apiClient";
import type { UserFormDTO } from "../models/UserFormDTO";
import type { UserDTO } from "../models/UserDTO";
import type { UserSimpleDTO } from "../models/UserSimpleDTO";
import { BasicService, createBasicService } from "./basicService";
import { UserImageDTO } from "../models/UserImageDTO";

export type UserService = ReturnType<typeof createUserService>;

export function createUserService(api: ApiClient) {
  const basicService = createBasicService(api);

  return {
    signUp: (body: UserFormDTO) => basicService.postMethod<UserDTO, UserFormDTO>("/users", body),
    getUserInfo: () => basicService.getMethod<UserSimpleDTO>("/users/me"),
    getUserById: (id: number) => basicService.getMethod<UserDTO>(`/users/${id}`),
    deleteUserById: (id: number) => basicService.deleteMethod<UserDTO>(`/users/${id}`),
    getExperiences: (id: number) => basicService.getMethod<UserDTO>(`/users/${id}/experiences`),
    getComments: (id: number) => basicService.getMethod<UserDTO>(`/users/${id}/comments`),
    updateUser: (id: number, body:UserDTO) => basicService.putMethod<UserDTO,UserDTO>(`/users/${id}`, body),
    updateUserImage: (id: number, multimedia:File) => updateUserImage(basicService, multimedia, id)
  };
}

async function updateUserImage(basicService: BasicService, multipartFile: File, userId: number) {

  const formData = new FormData();

  formData.append("multipartFile", multipartFile);

  return basicService.putMethod<UserImageDTO,FormData>(`/users/${userId}/image`, formData)
}