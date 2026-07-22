import { describe, expect, it, vi } from "vitest";
import { createUserService } from "../../../src/services/user.service";
import type { UserFormDTO } from "../../../src/models/UserFormDTO";

describe("UserService", () => {
  it("should successfully sign up a user when the request succeeds", async () => {
    const userFormData: UserFormDTO = {
      fullName: "Test User",
      displayName: "testuser",
      email: "test@example.com",
      password: "password123",
      passwordConfirmation: "password123",
    };

    const responseData = {
      id: 1,
      fullName: "Test User",
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
fullName:String;
    displayName: String;
    email: String;
  it("should pass the correct request body to the API", async () => {
    const userFormData: UserFormDTO = {
      fullName: "New User",
      displayName: "newuser",
      email: "newuser@example.com",
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
    } catch (e) {
      expect(textMock).toHaveBeenCalled();
    }
  });
});
