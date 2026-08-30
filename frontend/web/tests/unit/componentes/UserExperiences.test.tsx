import { beforeEach, describe, expect, it, vi } from "vitest";
import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import "@testing-library/jest-dom";

import UserExperiences from "../../../src/components/UserExperiences/UserExperiences";
import type { UserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";

describe("UserExperiences", () => {

  beforeEach(() => {
    useUserStore.setState({
      user: null,
    });
  });

  it("should render the experiences received as props", async () => {

    const fakeExperiences = [
      {
        id: 1,
        title: "My experience in Munich",
        date: "2026-06-25",
        cityName: "Munich",
        country: "Germany",
        categories:["Studies"],
        rating: 8.5,
        description: "Great experience",
        authorName: "Jeremy"
      },
      {
        id: 2,
        title: "Studying in Berlin",
        cityName: "Berlin",
        country: "Germany",
        date: "2026-06-26",
        categories:["Culture", "Social_events"],
        rating: 9,
        description: "Amazing city",
        authorName: "Sam"
      },
    ];

    const mockService: UserService = {
      getExperiences: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
      deleteUserById: vi.fn(),
      signUp: vi.fn(),
    };

    render(
      <MemoryRouter>
        <UserExperiences
          userService={mockService}
          userExperiences={fakeExperiences}
          userId={1}
        />
      </MemoryRouter>
    );

    expect(
      await screen.findByText("My experience in Munich")
    ).toBeInTheDocument();

    expect(
      screen.getByText("Studying in Berlin")
    ).toBeInTheDocument();

    expect(mockService.getExperiences).not.toHaveBeenCalled();
  });


  it("should get experiences from the service when no experiences are provided", async () => {

    const fakeExperiences = [
      {
        id: 1,
        title: "My experience in Munich",
        date: "2026-06-25",
        cityName: "Munich",
        country: "Germany",
        categories:["Studies"],
        rating: 8.5,
        description: "Great experience",
        authorName: "Jeremy"
      },
      {
        id: 2,
        title: "Studying in Berlin",
        cityName: "Berlin",
        country: "Germany",
        date: "2026-06-26",
        categories:["Culture", "Social_events"],
        rating: 9,
        description: "Amazing city",
        authorName: "Sam"
      },
    ];

    const mockGetExperiences = vi
      .fn()
      .mockResolvedValue(fakeExperiences);

    const mockService: UserService = {
      getExperiences: mockGetExperiences,
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
      deleteUserById: vi.fn(),
      signUp: vi.fn(),
    };

    render(
      <MemoryRouter>
        <UserExperiences
          userService={mockService}
          userExperiences={undefined}
          userId={1}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(mockGetExperiences).toHaveBeenCalledTimes(1);
      expect(mockGetExperiences).toHaveBeenCalledWith(1);
    });

    expect(
      await screen.findByText("My experience in Munich")
    ).toBeInTheDocument();

    expect(
      screen.getByText("Studying in Berlin")
    ).toBeInTheDocument();
  });


  it("should use the provided userId to get experiences", async () => {

    const fakeExperiences = [
      {
        id: 1,
        title: "User 2 experience",
        date: "2026-06-25",
        cityName: "Munich",
        country: "Germany",
        categories:["Studies"],
        rating: 8,
        description: "Great experience",
        authorName: "Jeremy"
      }
    ];

    const mockGetExperiences = vi
      .fn()
      .mockResolvedValue(fakeExperiences);

    const mockService: UserService = {
      getExperiences: mockGetExperiences,
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
      deleteUserById: vi.fn(),
      signUp: vi.fn(),
    };

    render(
      <MemoryRouter>
        <UserExperiences
          userService={mockService}
          userExperiences={undefined}
          userId={2}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(mockGetExperiences).toHaveBeenCalledWith(2);
    });

    expect(
      await screen.findByText("User 2 experience")
    ).toBeInTheDocument();
  });


  it("should use the logged-in user's id when userId is undefined", async () => {

    const fakeExperiences = [
      {
        id: 1,
        title: "My experience",
        date: "2026-06-25",
        cityName: "Munich",
        country: "Germany",
        categories:["Studies"],
        rating: 8,
        description: "Great experience",
        authorName: "Jeremy"
      }
    ];

    const mockGetExperiences = vi
      .fn()
      .mockResolvedValue(fakeExperiences);

    const mockService: UserService = {
      getExperiences: mockGetExperiences,
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
        <UserExperiences
          userService={mockService}
          userExperiences={undefined}
          userId={undefined}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(mockGetExperiences).toHaveBeenCalledWith(10);
    });

    expect(
      await screen.findByText("My experience")
    ).toBeInTheDocument();
  });

});