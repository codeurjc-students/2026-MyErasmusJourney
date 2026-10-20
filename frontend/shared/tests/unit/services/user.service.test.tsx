import { vi, describe, beforeEach, it, expect } from "vitest";
import { ApiClient } from "../../../src/api/apiClient";
import { createUserService } from "../../../src/services/user.service";
import type { UserService } from "../../../src/services/user.service";
import { UserDTO } from "../../../src/models/UserDTO";
import { UserFormDTO } from "../../../src/models/UserFormDTO";



const { basicServiceMock } = vi.hoisted(() => ({
  basicServiceMock: {
    getMethod: vi.fn(),
    postMethod: vi.fn(),
    putMethod: vi.fn(),
    deleteMethod: vi.fn(),
  },
}));

vi.mock("../../../src/services/basicService", () => ({
  createBasicService: vi.fn(() => basicServiceMock),
}));

describe("UserService", () => {
  let userService: UserService

  beforeEach(() => {
    vi.clearAllMocks();

    basicServiceMock.putMethod.mockResolvedValue(undefined);

    userService = createUserService({} as ApiClient);
  });

  describe("updateUserImage", () => {
    it("should send the image in FormData to the correct user endpoint", async () => {
      const userId = 7;

      const image = new File(["image content"], "profile.png", {
        type: "image/png",
      });

      await userService.updateUserImage(userId, image);

      expect(basicServiceMock.putMethod).toHaveBeenCalledExactlyOnceWith(
        `/users/${userId}/image`,
        expect.any(FormData)
      );

      const formData = basicServiceMock.putMethod.mock.calls[0][1] as FormData;

      expect(formData.get("multipartFile")).toBe(image);
      expect(formData.getAll("multipartFile")).toHaveLength(1);
    });
  });

  describe("delegated methods", () => {
    it("should call the correct endpoint for each method", async () => {
      const user = { id: 7 } as UserDTO;
      const userForm = {} as UserFormDTO;

      await userService.signUp(userForm);
      expect(basicServiceMock.postMethod).toHaveBeenCalledWith(
        "/users",
        userForm
      );

      await userService.getUserInfo();
      expect(basicServiceMock.getMethod).toHaveBeenCalledWith("/users/me");

      await userService.getUserById(7);
      expect(basicServiceMock.getMethod).toHaveBeenCalledWith("/users/7");

      await userService.deleteUserById(7);
      expect(basicServiceMock.deleteMethod).toHaveBeenCalledWith("/users/7");

      await userService.getExperiences(7);
      expect(basicServiceMock.getMethod).toHaveBeenCalledWith(
        "/users/7/experiences"
      );

      await userService.getComments(7);
      expect(basicServiceMock.getMethod).toHaveBeenCalledWith(
        "/users/7/comments"
      );

      await userService.updateUser(7, user);
      expect(basicServiceMock.putMethod).toHaveBeenCalledWith(
        "/users/7",
        user
      );
    });
  });
});