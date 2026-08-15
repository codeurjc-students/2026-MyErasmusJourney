import { render, waitFor } from "@testing-library/react";
import { describe, it, expect, vi, beforeEach } from "vitest";
import "@testing-library/jest-dom";
import { MemoryRouter } from "react-router-dom";
import App from "src/App";
import type { UserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";

describe("App", () => {

  beforeEach(() => {
    useUserStore.setState({
      user: null,
    });
  });

  it("should successfully fetch and store user information", async () => {

    //mocked data
    const fakeUser = {
      id: 1,
      displayName: "testuser",
      email: "test@example.com",
    };

    //mock of getUserInfo
    const mockGetUserInfo = vi.fn().mockResolvedValue(fakeUser);

    //return mocked service
    const mockService: UserService = {
      getUserInfo: mockGetUserInfo,
    };

    //render component (DOM virtual)
    render(
      <MemoryRouter>
        <App userService={mockService} />
      </MemoryRouter>
    );

    //assertions
    await waitFor(() => {
      expect(useUserStore.getState().user).toEqual(fakeUser);
    });

    //verifies the service was called
    expect(mockGetUserInfo).toHaveBeenCalledTimes(1);
  });

  it("should set user to null when fetching user information fails", async () => {

    //initial user
    const initialUser = {
      id: 1,
      displayName: "testuser",
      email: "test@example.com",
    };

    useUserStore.setState({
      user: initialUser,
    });

    //mock of getUserInfo
    const mockGetUserInfo = vi.fn().mockRejectedValue(
      new Error("Mocked error")
    );

    //return mocked service
    const mockService: UserService = {
      getUserInfo: mockGetUserInfo,
    };

    //render component (DOM virtual)
    render(
      <MemoryRouter>
        <App userService={mockService} />
      </MemoryRouter>
    );

    //assertions
    await waitFor(() => {
      expect(useUserStore.getState().user).toBeNull();
    });

    //verifies the service was called
    expect(mockGetUserInfo).toHaveBeenCalledTimes(1);
  });
});