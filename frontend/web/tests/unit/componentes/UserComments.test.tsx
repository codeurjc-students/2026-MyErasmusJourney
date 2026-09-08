import { beforeEach, describe, expect, it, vi } from "vitest";

import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import "@testing-library/jest-dom";

import UserComments from "../../../src/components/UserComments/UserComments";

import type { UserService } from "@shared/services/user.service";

import { useUserStore } from "@shared/stores/userStore";
import { ApiError } from "@shared/api/apiError";

const mockNavigate = vi.fn();

vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual<typeof import("react-router-dom")>(
    "react-router-dom"
  );

  return {
    ...actual,
    useNavigate: () => mockNavigate,
  };
});

describe("UserComments", () => {

  beforeEach(() => {
    useUserStore.setState({
      user: null,
    });
  });

  it("should render the comments received as props", async () => {

    const fakeComments = [
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
        authorName: "Sam",
        experienceId: 2
      },
    ];

    const mockService: UserService = {
      getExperiences: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
      deleteUserById: vi.fn(),
      signUp: vi.fn(),
      getComments: vi.fn(),
    };

    render(
      <MemoryRouter>
        <UserComments
          userService={mockService}
          userComments={fakeComments}
          userId={1}
        />
      </MemoryRouter>
    );

    expect(
      await screen.findByText("Great experience!")
    ).toBeInTheDocument();

    expect(
      screen.getByText("I really enjoyed reading this.")
    ).toBeInTheDocument();

    expect(mockService.getComments).not.toHaveBeenCalled();
  });


  it("should get comments from the service when no comments are provided", async () => {

    const fakeComments = [
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
        authorName: "Sam",
        experienceId: 2
      },
    ];

    const mockGetComments = vi
      .fn()
      .mockResolvedValue(fakeComments);

    const mockService: UserService = {
      getExperiences: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
      deleteUserById: vi.fn(),
      signUp: vi.fn(),
      getComments: mockGetComments,
    };

    render(
      <MemoryRouter>
        <UserComments
          userService={mockService}
          userComments={undefined}
          userId={1}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(mockGetComments).toHaveBeenCalledTimes(1);
      expect(mockGetComments).toHaveBeenCalledWith(1);
    });

    expect(
      await screen.findByText("Great experience!")
    ).toBeInTheDocument();

    expect(
      screen.getByText("I really enjoyed reading this.")
    ).toBeInTheDocument();
  });


  it("should use the provided userId to get comments", async () => {

    const fakeComments = [
      {
        id: 1,
        description: "Comment from another user.",
        date: "2026-06-25",
        authorName: "Jeremy",
        experienceId: 1,
      },
    ];

    const mockGetComments = vi
      .fn()
      .mockResolvedValue(fakeComments);

    const mockService: UserService = {
      getComments: mockGetComments,
      getExperiences: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
      deleteUserById: vi.fn(),
      signUp: vi.fn(),
    };

    render(
      <MemoryRouter>
        <UserComments
          userService={mockService}
          userComments={undefined}
          userId={2}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(mockGetComments).toHaveBeenCalledWith(2);
    });

    expect(
      await screen.findByText("Comment from another user.")
    ).toBeInTheDocument();
  });


  it("should use the logged-in user's id when userId is undefined", async () => {

    const fakeComments = [
      {
        id: 1,
        description: "My comment",
        date: "2026-06-25",
        authorName: "Jeremy",
        experienceId: 1,
      },
    ];

    const mockGetComments = vi
      .fn()
      .mockResolvedValue(fakeComments);

    const mockService: UserService = {
      getComments: mockGetComments,
      getExperiences: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
      deleteUserById: vi.fn(),
      signUp: vi.fn(),
    };

    useUserStore.setState({
      user: {
        id: 10,
        displayName: "John",
        email: "john@test.com",
      },
    });

    render(
      <MemoryRouter>
        <UserComments
          userService={mockService}
          userComments={undefined}
          userId={undefined}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(mockGetComments).toHaveBeenCalledWith(10);
    });

    expect(
      await screen.findByText("My comment")
    ).toBeInTheDocument();
  });

  it("navigates to /error when getting comments fails with server error (500)", async () => {
    const mockGetComments = vi.fn().mockRejectedValue(new ApiError(500, "Internal server error"));

    const mockService: UserService = {
      getComments: mockGetComments,
      getExperiences: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
      deleteUserById: vi.fn(),
      signUp: vi.fn(),
    };

    useUserStore.setState({
      user: {
        id: 10,
        displayName: "John",
        email: "john@test.com",
      },
    });

    render(
      <MemoryRouter>
        <UserComments
          userService={mockService}
          userComments={undefined}
          userId={undefined}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(mockGetComments).toHaveBeenCalledWith(10);
      expect(mockNavigate).toHaveBeenCalledWith("/error");
    });
  });

});