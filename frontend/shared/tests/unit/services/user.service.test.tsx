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

    const errorMessage = "Email already exists";

    const mockApi = {
      post: vi.fn().mockResolvedValue({
        ok: false,
        text: vi.fn().mockResolvedValue(errorMessage),
        json: vi.fn(),
      }),
    };

    const service = createUserService(mockApi);

    await expect(service.signUp(userFormData)).rejects.toThrow(errorMessage);
    expect(mockApi.post).toHaveBeenCalledWith("/users/", userFormData);
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
      studyLocation: "Munich, Germany"
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

  
});