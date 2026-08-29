
/**creates instance of service with all the methods the service offers,
 * after it can be used as an object in the component calling the
 * required method for each case
 * example:
 *  expService = createExperienceService
 *  data = expService.getAll();
**/

import type { ExperienceFormDTO } from "../models/ExperienceFormDTO";
import type { ApiClient } from "../apiClient";
import type { ExperiencePageDTO } from "../models/ExperienceSimpleDTO";
import type { CommentFormDTO } from "../models/CommentFormDTO";


export type ExperienceService = ReturnType<typeof createExperienceService>;

export function createExperienceService(api: ApiClient) {
  return {
    getAll: (page?: number, size?: number) => getAllExperiences(api, page, size),
    getCategories: () => getCategories(api),
    postExperience: (body: ExperienceFormDTO) => postExperience(api, body),
    getExperienceById: (id: number)=> getExperienceById(api, id),
    postComment:(id:number, body: CommentFormDTO) => postComment(api, id, body),
    getCommentsByExperienceId:(id:number) => getCommentsByExperienceId(api, id)
  };
}

async function getAllExperiences(api: ApiClient, page?: number, size?: number) {
  let url = "/experiences/";

  if (typeof page === "number" || typeof size === "number") {
    const params = new URLSearchParams();
    if (typeof page === "number") params.append("page", String(page));
    if (typeof size === "number") params.append("size", String(size));
    url = `${url}?${params.toString()}`;
  }

  const response = await api.get(url)

  if (!response.ok) {
    throw new Error("Error fetching experiences")
  }

  return response.json() as Promise<ExperiencePageDTO>;
}

async function getCategories(api: ApiClient) {
  const response = await api.get("/experiences/categories")

  if (!response.ok) {
    throw new Error("Error fetching categories")
  }

  return response.json();
}

async function postExperience(api: ApiClient, body: ExperienceFormDTO) {
  const response = await api.post("/experiences/", body);

  if (!response.ok) {
    throw new Error("Error posting new experience")
  }

  return response.json();
}

async function getExperienceById(api: ApiClient, id:number) {
  const response = await api.get(`/experiences/${id}`)

  if(response.status === 404) return null;

  if (!response.ok) {
    throw new Error("Error fetching experience")
  }

  return response.json();
}

async function postComment(api: ApiClient, id:number, body:CommentFormDTO){
   const response = await api.post(`/experiences/${id}/comments`, body)

  if (response.status !== 201) {
    throw new Error("Error posting new comment")
  }

  return response.json();
}

async function getCommentsByExperienceId(api: ApiClient, id:number){
   const response = await api.get(`/experiences/${id}/comments`)

  if (!response.ok) {
    throw new Error("Error posting new comment")
  }

  return response.json();
}