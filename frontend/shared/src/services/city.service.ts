import { ApiError } from "../api/apiError";
import type { ApiClient } from "../api/apiClient";
import type { CityDTO } from "../models/CityDTO";
import type { CityFormDTO } from "../models/CityFormDTO";
import type { CitySimpleDTO } from "../models/CitySimpleDTO";
import { createBasicService } from "./basicService";

export type CityService = ReturnType<typeof createCityService>;

export function createCityService(api:ApiClient) {
  const basicService = createBasicService(api)
  
  return {
    addCity: (body:CityFormDTO) => basicService.postMethod<CityDTO,CityFormDTO>("/cities/", body),
    getAll: () => basicService.getMethod<CitySimpleDTO[]>("/cities/"),
    getTrending: () => basicService.getMethod<CitySimpleDTO[]>("/cities/trending"),
    getCityById: (id: number) => basicService.getMethod<CityDTO>(`/cities/${id}`),
  };
} 