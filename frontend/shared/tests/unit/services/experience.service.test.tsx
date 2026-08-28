import { describe, expect, it, vi } from "vitest";
import { createExperienceService } from "../../../src/services/experience.service";

import type {ExperienceFormDTO} from "../../../src/models/ExperienceFormDTO"
import type {ExperienceSimpleDTO} from "../../../src/models/ExperienceSimpleDTO"
import type {ExperienceDTO} from "../../../src/models/ExperienceDTO"


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

    it("should return the experience when the request succeeds", async () => {
      const experience: ExperienceDTO = {
          id: 1,
          title: "Erasmus in Paris",
          date: "2025-03-01",
          rating: 9.1,
          description: "Amazing Erasmus experience",
          categories: ["Culture", "Social_Events"],
          city: {
              id: 1,
              name: "Paris",
              country: "France"
          },
          author: {
              id: 1,
              displayName: "author1",
              email: "author1@email.com"
          }
      };

      const mockApi = {
          get: vi.fn().mockResolvedValue({
              ok: true,
              status: 200,
              json: vi.fn().mockResolvedValue(experience)
          })
      };

      const service = createExperienceService(mockApi);

      const result = await service.getExperienceById(1);

      expect(mockApi.get).toHaveBeenCalledTimes(1);
      expect(mockApi.get).toHaveBeenCalledWith("/experiences/1");
      expect(result).toEqual(experience);
  });

  it("should return null when the experience does not exist", async () => {
    const mockApi = {
        get: vi.fn().mockResolvedValue({
            ok: false,
            status: 404
        })
    };

    const service = createExperienceService(mockApi);

    const result = await service.getExperienceById(999);

    expect(mockApi.get).toHaveBeenCalledTimes(1);
    expect(mockApi.get).toHaveBeenCalledWith("/experiences/999");
    expect(result).toBeNull();
  }); 

  it("should throw an error when fetching an experience fails", async () => {
    const mockApi = {
        get: vi.fn().mockResolvedValue({
            ok: false,
            status: 500
        })
    };

    const service = createExperienceService(mockApi);

    await expect(
        service.getExperienceById(1)
    ).rejects.toThrow("Error fetching experience");

    expect(mockApi.get).toHaveBeenCalledTimes(1);
    expect(mockApi.get).toHaveBeenCalledWith("/experiences/1");
  });

  it("should post a new comment successfully", async () => {

    const fakeComment: CommentSimpleDTO = {
      id: 1,
      description: "Great experience!",
      date: "2026-08-28",
      authorName: "John"
    };

    const mockPost = vi.fn().mockResolvedValue({
      status: 201,
      json: vi.fn().mockResolvedValue(fakeComment)
    });

    const mockApi: ApiClient = {
      post: mockPost,
      get: vi.fn(),
    };

    const experienceService = createExperienceService(mockApi);

    const comment: CommentFormDTO = {
      description: "Great experience!"
    };

    const result = await experienceService.postComment(1, comment);

    expect(mockPost).toHaveBeenCalledTimes(1);
    expect(mockPost).toHaveBeenCalledWith(
      "/experiences/1/comments",
      comment
    );

    expect(result).toEqual(fakeComment);
  });


  it("should throw an error when posting a comment fails", async () => {

    const mockPost = vi.fn().mockResolvedValue({
      status: 400,
      json: vi.fn()
    });

    const mockApi: ApiClient = {
      post: mockPost,
      get: vi.fn(),
    };

    const experienceService = createExperienceService(mockApi);

    const comment: CommentFormDTO = {
      description: "Great experience!"
    };

    await expect(
      experienceService.postComment(1, comment)
    ).rejects.toThrow("Error posting new comment");

    expect(mockPost).toHaveBeenCalledWith(
      "/experiences/1/comments",
      comment
    );
  });


  it("should get comments successfully", async () => {

    const fakeComments: CommentSimpleDTO[] = [
      {
        id: 1,
        description: "Great experience!",
        date: "2026-08-28",
        authorName: "John"
      },
      {
        id: 2,
        description: "I really enjoyed it.",
        date: "2026-08-27",
        authorName: "Jane"
      }
    ];

    const mockGet = vi.fn().mockResolvedValue({
      ok: true,
      json: vi.fn().mockResolvedValue(fakeComments)
    });

    const mockApi: ApiClient = {
      post: vi.fn(),
      get: mockGet,
    };

    const experienceService = createExperienceService(mockApi);

    const result = await experienceService.getCommentsByExperienceId(1);

    expect(mockGet).toHaveBeenCalledTimes(1);
    expect(mockGet).toHaveBeenCalledWith(
      "/experiences/1/comments"
    );

    expect(result).toEqual(fakeComments);
  });


  it("should throw an error when getting comments fails", async () => {

    const mockGet = vi.fn().mockResolvedValue({
      ok: false,
      json: vi.fn()
    });

    const mockApi: ApiClient = {
      post: vi.fn(),
      get: mockGet,
    };

    const experienceService = createExperienceService(mockApi);

    await expect(
      experienceService.getCommentsByExperienceId(1)
    ).rejects.toThrow("Error posting new comment");

    expect(mockGet).toHaveBeenCalledWith(
      "/experiences/1/comments"
    );
  });
});