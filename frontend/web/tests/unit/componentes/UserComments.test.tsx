import { beforeEach, describe, expect, it, vi } from "vitest";

import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import "@testing-library/jest-dom";

import UserComments from "../../../src/components/UserComments/UserComments";

import type { UserService } from "@shared/services/user.service";
import type { CommentDTO } from "@shared/models/CommentDTO";

import { useUserStore } from "@shared/stores/userStore";
import { ApiError } from "@shared/api/apiError";
import type { CommentService } from "@shared/services/comment.service";

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
      updateUser: vi.fn()

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
      updateUser: vi.fn()

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
      updateUser: vi.fn()

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
      updateUser: vi.fn()

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
      updateUser: vi.fn()

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

  it("should delete comment and reload the comments", async () => {

    const initialComments: CommentDTO[] = [
      {
        id: 1,
        date: "2026-06-25",
        description: "Comment that will be deleted",
        author: {
          id: 1,
          displayName: "Jeremy",
          email: ""
        },
        experience: {
          id: 1,
          title: "Experience",
          date: "2026-06-25",
          cityName: "Munich",
          country: "Germany",
          categories: ["Studies"],
          rating: 8.5,
          description: "Experience",
          authorName: "Jeremy",
        }
      },
      {
        id: 2,
        date: "2026-06-25",
        description: "Comment that will remain",
        author: {
          id: 1,
          displayName: "Jeremy",
          email: ""
        },
        experience: {
          id: 1,
          title: "Experience to delete",
          date: "2026-06-25",
          cityName: "Munich",
          country: "Germany",
          categories: ["Studies"],
          rating: 8.5,
          description: "Experience",
          authorName: "Jeremy",
        }
      },
    ];

    const remainingComments = [
      {
        id: 2,
        date: "2026-06-25",
        description: "Comment that will remain",
        author: {
          id: 1,
          displayName: "Jeremy",
          email: ""
        },
        experience: {
          id: 1,
          title: "Experience to delete",
          date: "2026-06-25",
          cityName: "Munich",
          country: "Germany",
          categories: ["Studies"],
          rating: 8.5,
          description: "Experience",
          authorName: "Jeremy",
        }
      },
    ];

    const mockGetComments = vi
      .fn()
      .mockResolvedValueOnce(initialComments)
      .mockResolvedValueOnce(remainingComments);

    const mockDeleteComment = vi
      .fn()
      .mockResolvedValue(initialComments.find(comment => comment.id === 1));

    const mockUserService: UserService = {
      getComments: mockGetComments,
      getExperiences: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
      deleteUserById: vi.fn(),
      signUp: vi.fn(),
      updateUser: vi.fn()

    };

    const mockCommentService: CommentService = {
      deleteComment: mockDeleteComment,
    };

    render(
      <MemoryRouter>
        <UserComments
          userService={mockUserService}
          commentService={mockCommentService}
          userComments={undefined}
          userId={1}
        />
      </MemoryRouter>
    );

    expect(
      await screen.findByText("Comment that will be deleted")
    ).toBeInTheDocument();

    await screen.getByRole("button", {
      name: "Delete Comment that will be deleted",
    }).click();

    await waitFor(() => {
      expect(mockDeleteComment).toHaveBeenCalledTimes(1);
      expect(mockDeleteComment).toHaveBeenCalledWith(1);
    });

    await waitFor(() => {
      expect(mockGetComments).toHaveBeenCalledTimes(2);
      expect(mockGetComments).toHaveBeenLastCalledWith(1);
    });

    expect(
      await screen.findByText("Comment that will remain")
    ).toBeInTheDocument();

    expect(
      screen.queryByText("Comment that will be deleted")
    ).not.toBeInTheDocument();
  });

  it("should show an alert when deleting an comment fails", async () => {
    const initialComments: CommentDTO[] = [
      {
        id: 1,
        date: "2026-06-25",
        description: "Comment that will be deleted",
        author: {
          id: 1,
          displayName: "Jeremy",
          email: ""
        },
        experience: {
          id: 1,
          title: "Experience",
          date: "2026-06-25",
          cityName: "Munich",
          country: "Germany",
          categories: ["Studies"],
          rating: 8.5,
          description: "Experience",
          authorName: "Jeremy",
        }
      },
    ]

    const mockDeleteComment = vi
      .fn()
      .mockRejectedValue(new ApiError(403, "Delete failed"));

    const mockGetComments = vi
      .fn()
      .mockResolvedValue(initialComments);

    const mockUserService: UserService = {
      getComments: mockGetComments,
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
      deleteUserById: vi.fn(),
      getExperiences: vi.fn(),
      signUp: vi.fn(),
      updateUser: vi.fn()

    };

    const mockCommentService: CommentService = {
      deleteComment: mockDeleteComment,
    };

    const alertSpy = vi
      .spyOn(window, "alert")
      .mockImplementation(() => { });

    render(
      <MemoryRouter>
        <UserComments
          userService={mockUserService}
          commentService={mockCommentService}
          userComments={undefined}
          userId={1}
        />
      </MemoryRouter>
    );

    expect(
      await screen.findByText("Comment that will be deleted")
    ).toBeInTheDocument();

    await screen.getByRole("button", {
      name: /^Delete /i,
    }).click();

    await waitFor(() => {
      expect(mockDeleteComment).toHaveBeenCalledWith(1);
    });

    expect(alertSpy).toHaveBeenCalledWith(
      "Failed to delete comment. Please try again later."
    );

    expect(mockGetComments).toHaveBeenCalledTimes(1);

    alertSpy.mockRestore();
  });

  it("should redirect to error page when deleting an experience fails in server", async () => {

    const initialComments: CommentDTO[] = [
      {
        id: 1,
        date: "2026-06-25",
        description: "Comment that will be deleted",
        author: {
          id: 1,
          displayName: "Jeremy",
          email: ""
        },
        experience: {
          id: 1,
          title: "Experience",
          date: "2026-06-25",
          cityName: "Munich",
          country: "Germany",
          categories: ["Studies"],
          rating: 8.5,
          description: "Experience",
          authorName: "Jeremy",
        }
      },
    ]

    const mockDeleteComment = vi
      .fn()
      .mockRejectedValue(new ApiError(500, "Delete failed"));

    const mockGetComments = vi
      .fn()
      .mockResolvedValue(initialComments);

    const mockUserService: UserService = {
      getComments: mockGetComments,
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
      deleteUserById: vi.fn(),
      signUp: vi.fn(),
      getExperiences: vi.fn(),
      updateUser: vi.fn()

    };

    const mockCommentService: CommentService = {
      deleteComment: mockDeleteComment,
    };

    render(
      <MemoryRouter>
        <UserComments
          userService={mockUserService}
          commentService={mockCommentService}
          userComments={undefined}
          userId={1}
        />
      </MemoryRouter>
    );

    expect(
      await screen.findByText("Comment that will be deleted")
    ).toBeInTheDocument();

    await screen.getByRole("button", {
      name: /^Delete /i,
    }).click();

    await waitFor(() => {
      expect(mockDeleteComment).toHaveBeenCalledWith(1);
    });

    expect(mockNavigate).toHaveBeenCalled();


  });

});