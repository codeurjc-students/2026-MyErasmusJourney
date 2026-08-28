import { beforeEach, describe, expect, it, vi } from "vitest";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { MemoryRouter } from "react-router-dom";

import Comments from "../../../src/components/Comments/Comments";
import type { ExperienceService } from "@shared/services/experience.service";
import { useUserStore } from "@shared/stores/userStore";


describe("Comments component", () => {

  beforeEach(() => {
    useUserStore.setState({
      user: null,
    });
  });


  it("should load and display comments", async () => {

    const fakeComments = [
      {
        id: 1,
        authorName: "John",
        date: "2026-08-20",
        description: "Great experience!",
      },
      {
        id: 2,
        authorName: "Jane",
        date: "2026-08-21",
        description: "I really enjoyed it!",
      },
    ];

    const mockGetComments = vi.fn().mockResolvedValue(fakeComments);

    const mockService: ExperienceService = {
      getCommentsByExperienceId: mockGetComments,
      postComment: vi.fn(),
      getAll: vi.fn(),
      getExperienceById: vi.fn(),
    };

    render(
      <MemoryRouter>
        <Comments
          experienceService={mockService}
          experienceId={1}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getByText("John")).toBeInTheDocument();
      expect(screen.getByText("Jane")).toBeInTheDocument();
      expect(screen.getByText("Great experience!")).toBeInTheDocument();
      expect(screen.getByText("I really enjoyed it!")).toBeInTheDocument();
    });

    expect(mockGetComments).toHaveBeenCalledTimes(1);
    expect(mockGetComments).toHaveBeenCalledWith(1);
  });


  it("should show login message when user is not authenticated", async () => {

    const mockGetComments = vi.fn().mockResolvedValue([]);

    const mockService: ExperienceService = {
      getCommentsByExperienceId: mockGetComments,
      postComment: vi.fn(),
      getAll: vi.fn(),
      getExperienceById: vi.fn(),
    };

    render(
      <MemoryRouter>
        <Comments
          experienceService={mockService}
          experienceId={1}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(
        screen.getByText(/Enjoyed this experience?/i)
      ).toBeInTheDocument();
    });

    expect(
      screen.getByRole("link", { name: /sign in/i })
    ).toBeInTheDocument();

    expect(
      screen.queryByPlaceholderText("Share your opinion...")
    ).not.toBeInTheDocument();
  });


  it("should allow an authenticated user to write and post a comment", async () => {

    const fakeUser = {
      id: 1,
      displayName: "John",
      email: "john@example.com",
    };

    const mockGetComments = vi
      .fn()
      .mockResolvedValue([]);

    const mockPostComment = vi
      .fn()
      .mockResolvedValue({});

    const mockService: ExperienceService = {
      getCommentsByExperienceId: mockGetComments,
      postComment: mockPostComment,
      getAll: vi.fn(),
      getExperienceById: vi.fn(),
    };

    useUserStore.setState({
      user: fakeUser,
    });

    render(
      <MemoryRouter>
        <Comments
          experienceService={mockService}
          experienceId={1}
        />
      </MemoryRouter>
    );

    const input = await screen.findByPlaceholderText(
      "Share your opinion..."
    );

    fireEvent.change(input, {
      target: {
        value: "This was a great experience!",
      },
    });

    expect(input).toHaveValue(
      "This was a great experience!"
    );

    fireEvent.click(
      screen.getByRole("button", {
        name: "Send comment",
      })
    );

    await waitFor(() => {
      expect(mockPostComment).toHaveBeenCalledWith(
        1,
        {
          description: "This was a great experience!",
        }
      );
    });
  });


  it("should reload comments and clear input after posting a comment", async () => {

    const fakeUser = {
      id: 1,
      displayName: "John",
      email: "john@example.com",
    };

    const initialComments = [
      {
        id: 1,
        authorName: "Jane",
        date: "2026-08-20",
        description: "Initial comment",
      },
    ];

    const updatedComments = [
      ...initialComments,
      {
        id: 2,
        authorName: "John",
        date: "2026-08-21",
        description: "New comment",
      },
    ];

    const mockGetComments = vi
      .fn()
      .mockResolvedValueOnce(initialComments)
      .mockResolvedValueOnce(updatedComments);

    const mockPostComment = vi
      .fn()
      .mockResolvedValue({});

    const mockService: ExperienceService = {
      getCommentsByExperienceId: mockGetComments,
      postComment: mockPostComment,
      getAll: vi.fn(),
      getExperienceById: vi.fn(),
    };

    useUserStore.setState({
      user: fakeUser,
    });

    render(
      <MemoryRouter>
        <Comments
          experienceService={mockService}
          experienceId={1}
        />
      </MemoryRouter>
    );

    const input = await screen.findByPlaceholderText(
      "Share your opinion..."
    );

    fireEvent.change(input, {
      target: {
        value: "New comment",
      },
    });

    fireEvent.click(
      screen.getByRole("button", {
        name: "Send comment",
      })
    );

    await waitFor(() => {
      expect(mockPostComment).toHaveBeenCalledWith(
        1,
        {
          description: "New comment",
        }
      );

      expect(mockGetComments).toHaveBeenCalledTimes(2);

      expect(
        screen.getByText("New comment")
      ).toBeInTheDocument();

      expect(input).toHaveValue("");
    });
  });
});