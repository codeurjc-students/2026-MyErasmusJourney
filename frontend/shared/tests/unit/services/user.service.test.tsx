import { describe, expect, it, vi } from "vitest";
import { createUserService } from "../../../src/services/user.service";
import type { UserFormDTO } from "../../../src/models/UserFormDTO";
import type { UserDTO } from "../../../src/models/UserDTO";


describe("UserService", () => {
  it("should successfully sign up a user when the request succeeds", async () => {
    const userFormData: UserFormDTO = {
      fullName: "Test User",
      displayName: "testuser",
      email: "test@example.com",
      city: "Madrid",
      country: "Spain",
      password: "password123",
      passwordConfirmation: "password123",
    };

    const responseData = {
      id: 1,
      displayName: "testuser",
      email: "test@example.com",
    };

    const mockApi = {
      post: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue(responseData),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);
    const result = await service.signUp(userFormData);

    expect(mockApi.post).toHaveBeenCalledTimes(1);
    expect(mockApi.post).toHaveBeenCalledWith("/users/", userFormData);
    expect(result).toEqual(responseData);
  });

  it("should throw an error when the sign up request fails", async () => {
    const userFormData: UserFormDTO = {
      fullName: "Test User",
      displayName: "testuser",
      email: "test@example.com",
      city: "Madrid",
      country: "Spain",
      password: "password123",
      passwordConfirmation: "password123",
    };

    const errorMessage = "Internal server error";
    const textMock = vi.fn().mockResolvedValue(errorMessage);

    const fakeResponse = {
      ok: false,
      status: 500,
      text: textMock,
    };

    const mockApi = {
      post: vi.fn().mockResolvedValue(fakeResponse),
    };

    const service = createUserService(mockApi);

    await expect(service.signUp(userFormData)).rejects.toThrow(errorMessage);
    expect(mockApi.post).toHaveBeenCalledWith("/users/", userFormData);
    expect(textMock).toHaveBeenCalledTimes(1);
  });

  it("should pass the correct request body to the API", async () => {
    const userFormData: UserFormDTO = {
      fullName: "New User",
      displayName: "newuser",
      email: "newuser@example.com",
      city: "Seville",
      country: "Spain",
      password: "securePass456",
      passwordConfirmation: "securePass456",
    };

    const mockApi = {
      post: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue({}),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await service.signUp(userFormData);

    expect(mockApi.post).toHaveBeenCalledWith("/users/", userFormData);
  });

  it("should call the text method to get the error message from failed response", async () => {
    const userFormData: UserFormDTO = {
      fullName: "Test User",
      displayName: "testuser",
      email: "test@example.com",
      city: "Madrid",
      country: "Spain",
      password: "password123",
      passwordConfirmation: "password123",
    };

    const errorMessage = "Validation error";
    const textMock = vi.fn().mockResolvedValue(errorMessage);

    const mockApi = {
      post: vi.fn().mockResolvedValue({
        ok: false,
        text: textMock,
        json: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    try {
      await service.signUp(userFormData);
    } catch {
      expect(textMock).toHaveBeenCalled();
    }
  });

  it("should successfully return the logged user information", async () => {
    const responseData = {
      id: 1,
      displayName: "testuser",
      email: "test@example.com",
    };

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue(responseData),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    const result = await service.getUserInfo();

    expect(mockApi.get).toHaveBeenCalledTimes(1);
    expect(mockApi.get).toHaveBeenCalledWith("/users/me");
    expect(result).toEqual(responseData);
  });

  it("should throw an error when getUserInfo request fails", async () => {
    const errorMessage = "Unauthorized";

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: false,
        text: vi.fn().mockResolvedValue(errorMessage),
        json: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await expect(service.getUserInfo()).rejects.toThrow(errorMessage);

    expect(mockApi.get).toHaveBeenCalledWith("/users/me");
  });

  it("should call the text method to get the error message from failed getUserInfo response", async () => {
    const textMock = vi.fn().mockResolvedValue("Unauthorized");

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: false,
        text: textMock,
        json: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    try {
      await service.getUserInfo();
    } catch {
      expect(textMock).toHaveBeenCalled();
    }
  });

  it("should call the correct endpoint when requesting user information", async () => {
    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue({}),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await service.getUserInfo();

    expect(mockApi.get).toHaveBeenCalledWith("/users/me");
  });

  it("should successfully return the user information", async () => {
    const responseData: UserDTO = {
      id: 1,
      displayName: "testuser",
      fullName: "Test User",
      email: "test@email.com",
      studyLocation: "Munich, Germany",
      roles: ["USER"]
    }

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue(responseData),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    const result = await service.getUserById(1);

    expect(mockApi.get).toHaveBeenCalledTimes(1);
    expect(mockApi.get).toHaveBeenCalledWith("/users/1");
    expect(result).toEqual(responseData);
  });

  it("should throw an error when getUserById request fails", async () => {
    const errorMessage = "Unauthorized";

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: false,
        text: vi.fn().mockResolvedValue(errorMessage),
        json: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await expect(service.getUserById(1)).rejects.toThrow(errorMessage);

    expect(mockApi.get).toHaveBeenCalledWith("/users/1");
  });

  it("should call the text method to get the error message when user does not exist", async () => {
    const textMock = vi.fn().mockResolvedValue("User not found");

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: false,
        text: textMock,
        json: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    try {
      await service.getUserById(-1);
    } catch {
      expect(textMock).toHaveBeenCalled();
    }
  });

  it("should call the correct endpoint when requesting user information", async () => {
    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue({}),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await service.getUserById(5);

    expect(mockApi.get).toHaveBeenCalledWith("/users/5");
  });

  it("should successfully delete a user", async () => {
    const responseData = {
      id: 1,
      displayName: "testuser",
      email: "test@example.com"
    };

    const mockApi = {
      delete: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue(responseData),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    const result = await service.deleteUserById(1);

    expect(mockApi.delete).toHaveBeenCalledTimes(1);
    expect(mockApi.delete).toHaveBeenCalledWith("/users/1");
    expect(result).toEqual(responseData);
  });

  it("should throw an error when deleteUserById request fails", async () => {
    const errorMessage = "Unauthorized";

    const mockApi = {
      delete: vi.fn().mockResolvedValue({
        ok: false,
        text: vi.fn().mockResolvedValue(errorMessage),
        json: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await expect(
      service.deleteUserById(1)
    ).rejects.toThrow(errorMessage);

    expect(mockApi.delete).toHaveBeenCalledWith("/users/1");
  });

  it("should call the correct endpoint when deleting a user", async () => {
    const mockApi = {
      delete: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue({}),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await service.deleteUserById(5);

    expect(mockApi.delete).toHaveBeenCalledWith("/users/5");
  });

  it("should successfully return the experiences of a user", async () => {
    const responseData = [
      {
        id: 1,
        date: "2026-08-30",
        rating: 8.5,
        title: "My Erasmus Experience",
        description: "Amazing experience in Munich",
        categories: ["Studies", "Culture"],
        cityName: "Munich",
        country: "Germany",
        authorName: "testuser"
      },
      {
        id: 2,
        date: "2026-08-29",
        rating: 9.0,
        title: "A weekend in Berlin",
        description: "Great weekend trip",
        categories: ["Culture"],
        cityName: "Berlin",
        country: "Germany",
        authorName: "testuser"
      }
    ];

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue(responseData),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    const result = await service.getExperiences(1);

    expect(mockApi.get).toHaveBeenCalledTimes(1);
    expect(mockApi.get).toHaveBeenCalledWith("/users/1/experiences");
    expect(result).toEqual(responseData);
  });

  it("should throw an error when getExperiences request fails", async () => {
    const errorMessage = "Unauthorized";

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: false,
        text: vi.fn().mockResolvedValue(errorMessage),
        json: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await expect(
      service.getExperiences(1)
    ).rejects.toThrow(errorMessage);

    expect(mockApi.get).toHaveBeenCalledWith("/users/1/experiences");
  });

  it("should call the correct endpoint when requesting user experiences", async () => {
    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue([]),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await service.getExperiences(5);

    expect(mockApi.get).toHaveBeenCalledWith("/users/5/experiences");
  });

  it("should successfully return the comments of a user", async () => {
    const responseData = [
      {
        id: 1,
        description: "Great experience!",
        date: "2026-06-25",
        authorName: "Jeremy",
        experienceId: 1,
      },
      {
        id: 2,
        description: "I really enjoyed reading this.",
        date: "2026-06-26",
        authorName: "Jeremy",
        experienceId: 2
      },
    ];

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue(responseData),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    const result = await service.getComments(1);

    expect(mockApi.get).toHaveBeenCalledTimes(1);
    expect(mockApi.get).toHaveBeenCalledWith("/users/1/comments");
    expect(result).toEqual(responseData);
  });

  it("should throw an error when getComments request fails", async () => {
    const errorMessage = "Unauthorized";

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: false,
        text: vi.fn().mockResolvedValue(errorMessage),
        json: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await expect(
      service.getComments(1)
    ).rejects.toThrow(errorMessage);

    expect(mockApi.get).toHaveBeenCalledWith("/users/1/comments");
  });

  it("should call the correct endpoint when requesting user comments", async () => {
    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue([]),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await service.getComments(5);

    expect(mockApi.get).toHaveBeenCalledWith("/users/5/comments");
  });

  it("should successfully return the updated user", async () => {
    const responseData: UserDTO =
    {
      id: 1,
      fullName: "Jeremy Belpois",
      displayName: "Jeremy",
      email: "jeremy@email.com",
      studyLocation: "Paris, France",
      roles: ["USER"],
      experiences: [],
      comments: []
    }

    const mockApi = {
      put: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue(responseData),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    const result = await service.updateUser(1, responseData);

    expect(mockApi.put).toHaveBeenCalledTimes(1);
    expect(mockApi.put).toHaveBeenCalledWith("/users/1", responseData);
    expect(result).toEqual(responseData);
  });

  it("should throw an error when updating an user fails", async () => {

    const requestData: UserDTO =
    {
      id: 1,
      fullName: "Jeremy Belpois",
      displayName: "Jeremy",
      email: "jeremy@email.com",
      studyLocation: "Paris, France",
      roles: ["USER"],
      experiences: [],
      comments: []
    }

    const errorMessage = "Unauthorized";

    const mockApi = {
      put: vi.fn().mockResolvedValue({
        ok: false,
        text: vi.fn().mockResolvedValue(errorMessage),
        json: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await expect(
      service.updateUser(1, requestData)
    ).rejects.toThrow(errorMessage);

    expect(mockApi.put).toHaveBeenCalledWith("/users/1", requestData);
  });

  it("should call the correct endpoint when updating the user information", async () => {
    
    const requestData: UserDTO =
    {
      id: 1,
      fullName: "Jeremy Belpois",
      displayName: "Jeremy",
      email: "jeremy@email.com",
      studyLocation: "Paris, France",
      roles: ["USER"],
      experiences: [],
      comments: []
    }

    const mockApi = {
      put: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue([]),
        text: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await service.updateUser(5, requestData);

    expect(mockApi.put).toHaveBeenCalledWith("/users/5", requestData);
  });

});