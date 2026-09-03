import { ApiError } from "../api/apiError";
import type { ApiClient } from "../api/apiClient";
import type { CityFormDTO } from "../models/CityFormDTO";

export type CityService = ReturnType<typeof createCityService>;

export function createCityService(api:ApiClient) {
  return {
    addCity: (body:CityFormDTO) => addCity(api, body),
    getAll: () => getAll(api),
  };
}

async function addCity(api: ApiClient, body: CityFormDTO){
    const response = await api.post("/cities/", body);

    
    if (!response.ok){
      throw new ApiError(response.status, await response.text());
    }

    if (response.status === 200){
      throw new ApiError(response.status, await response.text());
    }

    return await response.json();
}

async function getAll(api: ApiClient){
    const response = await api.get("/cities/");
    
    if (!response.ok){
      throw new ApiError(response.status, await response.text());
    }

    return await response.json();
}