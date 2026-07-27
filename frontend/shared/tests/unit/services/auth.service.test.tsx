import { describe, expect, it, vi } from "vitest";
import { createAuthService } from "../../../src/services/auth.service";
import type { LoginRequest } from "../../../src/models/LoginRequest";

describe("AuthService", () => {

  it("should successfully log in when the request succeeds", async () => {
    const loginRequest: LoginRequest = {
      username: "test@example.com",
      password: "password123",
    };

    const responseData = {
      token: "fake-jwt-token",
    };

    const mockApi = {
      post: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue(responseData),
      }),
    };

    const service = createAuthService(mockApi);

    const result = await service.logIn(loginRequest);

    expect(mockApi.post).toHaveBeenCalledTimes(1);
    expect(mockApi.post).toHaveBeenCalledWith("/auth/login", loginRequest);
    expect(result).toEqual(responseData);
  });

  it("should throw an error when the login request fails", async () => {
    const loginRequest: LoginRequest = {
      username: "test@example.com",
      password: "password123",
    };

    const mockApi = {
      post: vi.fn().mockResolvedValue({
        ok: false,
        json: vi.fn(),
      }),
    };

    const service = createAuthService(mockApi);

    await expect(service.logIn(loginRequest)).rejects.toThrow(
      "Error loggin in"
    );

    expect(mockApi.post).toHaveBeenCalledWith("/auth/login", loginRequest);
  });

  it("should pass the correct request body to the API", async () => {
    const loginRequest: LoginRequest = {
      username: "another@example.com",
      password: "securePassword456",
    };

    const mockApi = {
      post: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue({}),
      }),
    };

    const service = createAuthService(mockApi);

    await service.logIn(loginRequest);

    expect(mockApi.post).toHaveBeenCalledWith("/auth/login", loginRequest);
  });

});