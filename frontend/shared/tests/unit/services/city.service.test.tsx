
import { vi, describe, beforeEach, it, expect } from "vitest";
import { ApiClient } from "../../../src/api/apiClient";
import { ApiError } from "../../../src/api/apiError";
import { createCityService } from "../../../src/services/city.service";
import type { CityService } from "../../../src/services/city.service";
import type { CityDTO } from "../../../src/models/CityDTO";
import type { CityFormDTO } from "../../../src/models/CityFormDTO";

const { basicServiceMock, apiMock } = vi.hoisted(() => ({
  basicServiceMock: {
    getMethod: vi.fn(),
    postMethod: vi.fn(),
    putMethod: vi.fn(),
    deleteMethod: vi.fn(),
  },
  apiMock: {
    post: vi.fn(),
  },
}));

vi.mock("../../../src/services/basicService", () => ({
  createBasicService: vi.fn(() => basicServiceMock),
}));

describe("CityService", () => {
  let cityService: CityService;

  beforeEach(() => {
    vi.clearAllMocks();

    basicServiceMock.getMethod.mockResolvedValue([]);
    apiMock.post.mockResolvedValue({
      ok: true,
      status: 201,
      json: vi.fn().mockResolvedValue({ id: 1 }),
      text: vi.fn().mockResolvedValue(""),
    });

    cityService = createCityService(apiMock as unknown as ApiClient);
  });

  describe("addCity", () => {
    const cityForm = {
      name: "Madrid",
      country: "Spain",
      description: "Capital of Spain"
    } as CityFormDTO;

    it("should create a city and return the response body when status is 201", async () => {
      const city = {
        id: 1,
        name: "Madrid",
        country: "Spain",
      } as CityDTO;

      apiMock.post.mockResolvedValue({
        ok: true,
        status: 201,
        json: vi.fn().mockResolvedValue(city),
        text: vi.fn(),
      });

      await expect(cityService.addCity(cityForm)).resolves.toEqual(city);

      expect(apiMock.post).toHaveBeenCalledExactlyOnceWith(
        "/cities/",
        cityForm
      );
    });

    it("should throw ApiError with the response text when the response is not ok", async () => {
      apiMock.post.mockResolvedValue({
        ok: false,
        status: 500,
        text: vi.fn().mockResolvedValue("Internal server error"),
        json: vi.fn(),
      });

      await expect(cityService.addCity(cityForm)).rejects.toMatchObject({
        status: 500,
        message: "Internal server error",
      });

      expect(apiMock.post).toHaveBeenCalledExactlyOnceWith(
        "/cities/",
        cityForm
      );
    });

    it("should throw ApiError when the status is not 201", async () => {
      apiMock.post.mockResolvedValue({
        ok: true,
        status: 200,
        text: vi.fn(),
        json: vi.fn(),
      });

      await expect(cityService.addCity(cityForm)).rejects.toMatchObject({
        status: 200,
        message: "City is already registered",
      });
    });
  });

  describe("delegated methods", () => {
    it("should call the correct endpoint for each method", async () => {
      await cityService.getAll();
      expect(basicServiceMock.getMethod).toHaveBeenCalledWith("/cities/");

      await cityService.getTrending();
      expect(basicServiceMock.getMethod).toHaveBeenCalledWith(
        "/cities/trending"
      );

      await cityService.getCityById(7);
      expect(basicServiceMock.getMethod).toHaveBeenCalledWith("/cities/7");
    });
  });
});
