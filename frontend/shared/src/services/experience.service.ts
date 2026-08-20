
/**creates instance of service with all the methods the service offers,
 * after it can be used as an object in the component calling the
 * required method for each case
 * example:
 *  expService = createExperienceService
 *  data = expService.getAll();
**/

import type { ExperienceFormDTO } from "@shared/models/ExperienceFormDTO";
import type { ApiClient } from "../apiClient";

export type ExperienceService = ReturnType<typeof createExperienceService>;

export function createExperienceService(api: ApiClient) {
  return {
    getAll: () => getAllExperiences(api),
    getCategories: () => getCategories(api),
    postExperience: (body: ExperienceFormDTO) => postExperience(api, body)
  };
}

async function getAllExperiences(api: ApiClient) {
  const response = await api.get("/experiences/")

  if (!response.ok) {
    throw new Error("Error fetching experiences")
  }

  return response.json();
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
    throw new Error("Error creating new experience: " + response.body)
  }

  return response.json();
}