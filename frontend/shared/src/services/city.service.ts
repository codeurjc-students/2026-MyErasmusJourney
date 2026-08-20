import type { ApiClient } from "../apiClient";
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
        throw new Error("Error adding city");
    }

    if (response.status === 200){
        throw new Error("City already exists");
    }

    return await response.json();
}

async function getAll(api: ApiClient){
    const response = await api.get("/cities/");
    
    if (!response.ok){
        throw new Error("Error getting cities");
    }

    return await response.json();
}