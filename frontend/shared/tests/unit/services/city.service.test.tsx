import { describe, it, expect, vi } from "vitest";
import { createCityService } from "../../../src/services/city.service";
import { ApiClient } from "../../../src/apiClient";
import { CityFormDTO } from "../../../src/models/CityFormDTO";

describe("CityService", () => {

  it("should successfully add a city", async () => {

    const cityFormDTO: CityFormDTO = {
      name: "Madrid",
      country: "Spain",
      description: "A beautiful city",
    };

    const fakeResponse = {
      ok: true,
      status: 201,
      json: vi.fn().mockResolvedValue({
        id: 1,
        name: "Madrid",
        country: "Spain",
        description: "A beautiful city",
      }),
    };

    const mockPost = vi.fn().mockResolvedValue(fakeResponse);

    const mockAPI: ApiClient = {
      get: vi.fn(),
      post: mockPost,
    };

    const cityService = createCityService(mockAPI);

    const result = await cityService.addCity(cityFormDTO);

    expect(mockPost).toHaveBeenCalledWith(
      "/cities/",
      cityFormDTO
    );

    expect(mockPost).toHaveBeenCalledTimes(1);

    expect(result).toEqual({
      id: 1,
      name: "Madrid",
      country: "Spain",
      description: "A beautiful city",
    });

    expect(fakeResponse.json).toHaveBeenCalledTimes(1);
  });


  it("should throw an error when the city already exists", async () => {

    const cityFormDTO: CityFormDTO = {
      name: "Madrid",
      country: "Spain",
      description: "A beautiful city",
    };

    const fakeResponse = {
      ok: true,
      status: 200,
      json: vi.fn(),
    };

    const mockPost = vi.fn().mockResolvedValue(fakeResponse);

    const mockAPI: ApiClient = {
      get: vi.fn(),
      post: mockPost,
    };

    const cityService = createCityService(mockAPI);

    await expect(
      cityService.addCity(cityFormDTO)
    ).rejects.toThrow("City already exists");

    expect(mockPost).toHaveBeenCalledWith(
      "/cities/",
      cityFormDTO
    );

    expect(fakeResponse.json).not.toHaveBeenCalled();
  });


  it("should throw an error when the API request fails", async () => {

    const cityFormDTO: CityFormDTO = {
      name: "Madrid",
      country: "Spain",
      description: "A beautiful city",
    };

    const fakeResponse = {
      ok: false,
      status: 500,
      json: vi.fn(),
    };

    const mockPost = vi.fn().mockResolvedValue(fakeResponse);

    const mockAPI: ApiClient = {
      get: vi.fn(),
      post: mockPost,
    };

    const cityService = createCityService(mockAPI);

    await expect(
      cityService.addCity(cityFormDTO)
    ).rejects.toThrow("Error adding city");

    expect(mockPost).toHaveBeenCalledWith(
      "/cities/",
      cityFormDTO
    );

    expect(fakeResponse.json).not.toHaveBeenCalled();
  });


  it("should return the response JSON when the city is created", async () => {

    const cityFormDTO: CityFormDTO = {
      name: "Madrid",
      country: "Spain",
      description: "A beautiful city",
    };

    const responseData = {
      id: 1,
      name: "Madrid",
      country: "Spain",
      description: "A beautiful city",
    };

    const fakeResponse = {
      ok: true,
      status: 201,
      json: vi.fn().mockResolvedValue(responseData),
    };

    const mockPost = vi.fn().mockResolvedValue(fakeResponse);

    const mockAPI: ApiClient = {
      get: vi.fn(),
      post: mockPost,
    };

    const cityService = createCityService(mockAPI);

    const result = await cityService.addCity(cityFormDTO);

    expect(result).toEqual(responseData);
    expect(fakeResponse.json).toHaveBeenCalledTimes(1);
  });

});