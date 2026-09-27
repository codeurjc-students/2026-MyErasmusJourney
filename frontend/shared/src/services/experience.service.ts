
/**creates instance of service with all the methods the service offers,
 * after it can be used as an object in the component calling the
 * required method for each case
 * example:
 *  expService = createExperienceService
 *  data = expService.getAll();
**/

import type { ExperienceFormDTO } from "../models/ExperienceFormDTO";
import type { ApiClient } from "../api/apiClient";
import type { ExperiencePageDTO } from "../models/ExperienceSimpleDTO";
import type { CommentFormDTO } from "../models/CommentFormDTO";
import type { ExperienceFilters } from "../models/ExperienceFilters";
import { ApiError } from "../api/apiError";


export type ExperienceService = ReturnType<typeof createExperienceService>;

export function createExperienceService(api: ApiClient) {
  return {
    getAll: (filters: ExperienceFilters) => getAllExperiences(api, filters),
    getCategories: () => getCategories(api),
    postExperience: (body: ExperienceFormDTO) => postExperience(api, body),
    getExperienceById: (id: number) => getExperienceById(api, id),
    postComment: (id: number, body: CommentFormDTO) => postComment(api, id, body),
    getCommentsByExperienceId: (id: number) => getCommentsByExperienceId(api, id),
    deleteExperience: (id: number) => deleteExperience(api, id)
  };
}

async function getAllExperiences(api: ApiClient, filters?: ExperienceFilters) {
  let url = "/experiences/";
  const params = new URLSearchParams();

  if (typeof filters?.page === "number") params.append("page", String(filters.page));
  else params.append("page", String(0))
  if (typeof filters?.size === "number") params.append("size", String(filters.size));
  else params.append("size", String(6));
  if (typeof filters?.minRating === "number") params.append("minimumRate", String(filters.minRating));
  if (typeof filters?.maxRating === "number") params.append("maximumRate", String(filters.maxRating));
  if (filters?.from) params.append("from", filters.from);
  if (filters?.to) params.append("to", filters.to);
  if (typeof filters?.cityName === "string") params.append("cityName", String(filters.cityName));

  filters?.categories?.forEach(category => {
    params.append("categories", category);
  });

  if (params.toString()) {
    url = `${url}?${params.toString()}`;
  }

  console.log(params)

  const response = await api.get(url);

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());
  }

  return response.json() as Promise<ExperiencePageDTO>;
}

async function getCategories(api: ApiClient) {
  const response = await api.get("/experiences/categories");

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());
  }

  return response.json();
}

async function postExperience(api: ApiClient, body: ExperienceFormDTO) {
  const response = await api.post("/experiences/", body);

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());
  }

  return response.json();
}

async function getExperienceById(api: ApiClient, id: number) {
  const response = await api.get(`/experiences/${id}`)

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());
  }

  return response.json();
}

async function postComment(api: ApiClient, id: number, body: CommentFormDTO) {
  const response = await api.post(`/experiences/${id}/comments`, body)

  if (response.status !== 201) {
    throw new ApiError(response.status, await response.text());
  }

  return response.json();
}

async function getCommentsByExperienceId(api: ApiClient, id: number) {
  const response = await api.get(`/experiences/${id}/comments`)

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());
  }

  return response.json();
}

async function deleteExperience(api: ApiClient, id: number) {
  const response = await api.delete(`/experiences/${id}`)

  if (!response.ok) {
    throw new ApiError(response.status, await response.text());

  }

  return response.json();
}