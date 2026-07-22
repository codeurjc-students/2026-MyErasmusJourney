
/**creates instance of service with all the methods the service offers,
 * after it can be used as an object in the component calling the
 * required method for each case
 * example:
 *  expService = createExperienceService
 *  data = expService.getAll();
**/

import type { ApiClient } from "../apiClient";

export type ExperienceService = ReturnType<typeof createExperienceService>;

export function createExperienceService(api:ApiClient) {
  return {
    getAll: () => getAllExperiences(api)
  };
}

async function getAllExperiences(api: ApiClient){
    const response = await api.get("/experiences/")

    if (!response.ok){
        throw new Error("Error fetching experiences")
    }

    return response.json();
}