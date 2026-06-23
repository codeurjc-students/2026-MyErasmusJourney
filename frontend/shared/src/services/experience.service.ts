
/**creates instance of service with all the methods the service offers,
 * after it can be used as an object in the component calling the
 * required method for each case
 * example:
 *  expService = createExperienceService
 *  data = expService.getAll();
**/
export function createExperienceService(api:any) {
  return {
    getAll: () => getAllExperiences(api)
  };
}

async function getAllExperiences(api: any){
    const response = await api.get("/experiences/")

    if (!response.ok){
        throw new Error("Error fetching experiences")
    }

    return response.json();
}