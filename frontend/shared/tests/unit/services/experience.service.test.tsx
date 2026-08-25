import { describe, expect, it, vi } from "vitest";
import { createExperienceService } from "../../../src/services/experience.service";

import type {ExperienceFormDTO} from "../../../src/models/ExperienceFormDTO"
import type {ExperienceSimpleDTO} from "../../../src/models/ExperienceSimpleDTO"

describe("ExperienceService", () => {
  it("should return all experiences when the request succeeds", async () => {

    const experiences: ExperienceSimpleDTO[] = [
      { id: 1, title: "Experience 1",description: "description 1", date:"2022-02-12", rating:9.1, cityName: "Paris", country: "France", authorName: "author1"  },
      { id: 2, title: "Experience 2",description: "description 2", date:"2025-03-01", rating:1.5, cityName: "Rome", country: "Italy", authorName: "author2"  }
    ];

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue({ content: experiences }),
      }),
    };

    const service = createExperienceService(mockApi);

    const result = await service.getAll(0, 10);

    expect(mockApi.get).toHaveBeenCalledTimes(1);
    expect(mockApi.get).toHaveBeenCalledWith("/experiences/?page=0&size=10");
    expect(result.content).toEqual(experiences);
  });

  it("should throw an error when the request fails", async () => {
    
    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: false,
      }),
    };

    const service = createExperienceService(mockApi);

    await expect(service.getAll(1, 5)).rejects.toThrow(
      "Error fetching experiences"
    );

    expect(mockApi.get).toHaveBeenCalledWith("/experiences/?page=1&size=5");
  });

  it("should return all categories", async () => {

    const experiences = [
      "Acommodation", "Transportation"
    ];

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue(experiences),
      }),
    };

    const service = createExperienceService(mockApi);

    const result = await service.getCategories();

    expect(mockApi.get).toHaveBeenCalledTimes(1);
    expect(mockApi.get).toHaveBeenCalledWith("/experiences/categories");
    expect(result).toEqual(experiences);
  });

  it("should throw an error when the category request fails", async () => {
    
    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: false,
      }),
    };

    const service = createExperienceService(mockApi);

    await expect(service.getCategories()).rejects.toThrow(
      "Error fetching categories"
    );

    expect(mockApi.get).toHaveBeenCalledWith("/experiences/categories");
  });

  it("should return the response JSON when the experience is posted", async () => {
  
      const experienceFormDTO: ExperienceFormDTO = {
        title: "test",
        date: "2020-03-22",
        description: "test description",
        categories: ["Studies", "Personal_Experience"],
        rating: 4.3,
        cityId: 1
      }
  
      const responseData = {
        id: 1,
        title: "test",
        date: "2020-03-22",
        description: "test description",
        categories: ["Studies", "Personal_Experience"],
        rating: 4.3
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
  
      const experienceService = createExperienceService(mockAPI);
  
      const result = await experienceService.postExperience(experienceFormDTO);
  
      expect(result).toEqual(responseData);
      expect(fakeResponse.json).toHaveBeenCalledTimes(1);
    });

    it("should throw error when posting experience fails", async () => {
  
      const experienceFormDTO: ExperienceFormDTO = {
        title: "test",
        date: "2020-03-22",
        description: "test description",
        categories: ["Studies", "Personal_Experience",],
        rating: 4.3,
        cityId: 1
      }
  
      const fakeResponse = {
        ok: false,
        json: vi.fn().mockReturnValue("Unable to post experience"),
      };
  
      const mockPost = vi.fn().mockResolvedValue(fakeResponse);
  
      const mockAPI: ApiClient = {
        get: vi.fn(),
        post: mockPost,
      };
  
      const experienceService = createExperienceService(mockAPI);
  
      await expect(experienceService.postExperience(experienceFormDTO)).rejects.toThrow(
        "Error posting new experience"
      );
    });
});