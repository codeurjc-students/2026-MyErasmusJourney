
/**creates instance of service with all the methods the service offers,
 * after it can be used as an object in the component calling the
 * required method for each case
 * example:
 *  expService = createExperienceService
 *  data = expService.getAll();
**/

import type { ApiClient } from "../api/apiClient";
import type { CommentDTO } from "../models/CommentDTO";
import type { CommentFormDTO } from "../models/CommentFormDTO";
import type { ExperienceDTO } from "../models/ExperienceDTO";
import type { ExperienceFilters } from "../models/ExperienceFilters";
import type { ExperienceFormDTO } from "../models/ExperienceFormDTO";
import type { ExperiencePageDTO } from "../models/ExperienceSimpleDTO";
import { type BasicService, createBasicService } from "./basicService";
import type { MultimediaSimpleDTO } from "../models/MultimediaSimpleDTO";
import type { CommentSimpleDTO } from "@shared/models/CommentSimpleDTO";


export type ExperienceService = ReturnType<typeof createExperienceService>;

export function createExperienceService(api: ApiClient) {
  const basicService = createBasicService(api);

  return {
    getAll: (filters: ExperienceFilters) => getAllExperiences(basicService, filters),
    getCategories: () => basicService.getMethod<string[]>("/experiences/categories"),
    postExperience: (body: ExperienceFormDTO) => basicService.postMethod<ExperienceDTO, ExperienceFormDTO>("/experiences/", body),
    addMultimedia: (multimedia: File[], experienceId: number) => addMultimedia(basicService, multimedia, experienceId),
    getExperienceById: (id: number) => basicService.getMethod<ExperienceDTO>(`/experiences/${id}`),
    postComment: (id: number, body: CommentFormDTO) => basicService.postMethod<CommentDTO, CommentFormDTO>(`/experiences/${id}/comments`, body),
    getCommentsByExperienceId: (id: number) => basicService.getMethod<CommentSimpleDTO[]>(`/experiences/${id}/comments`),
    deleteExperience: (id: number) => basicService.deleteMethod(`/experiences/${id}`)
  };
}

async function getAllExperiences(basicService: BasicService, filters?: ExperienceFilters) {
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

  return basicService.getMethod<ExperiencePageDTO>(url);
}


async function addMultimedia(basicService: BasicService, multipartFileList: File[], experienceId: number) {

  const formData = new FormData();

  multipartFileList.forEach(file => {
    formData.append("multipartFileList", file);

  });

  return basicService.postMethod<MultimediaSimpleDTO[], FormData>(`/experiences/${experienceId}/multimedia`, formData)
}