import { beforeEach, describe, expect, it, vi } from "vitest";
import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import "@testing-library/jest-dom";

import DetailedExperiencePage from "../../../src/pages/DetailedExperiencePage/DetailedExperiencePage";
import type { ExperienceService } from "@shared/services/experience.service";

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

describe("DetailedExperiencePage", () => {

  beforeEach(() => {
    mockNavigate.mockClear();
  });

  const fakeExperience = {
    id: 1,
    title: "My Erasmus Experience",
    date: "2026-06-25",
    rating: 8.5,
    description: "This was an amazing Erasmus experience.",
    categories: [
      "CULTURE",
      "GASTRONOMY",
    ],
    city: {
      id: 1,
      name: "Madrid",
      country: "Spain",
    },
    author: {
      id: 1,
      displayName: "John",
      email: "john@example.com",
    },
  };

  it("should show loading message while fetching the experience", () => {

    const mockGetExperienceById = vi.fn(
      () => new Promise(() => {})
    );

    const mockService: ExperienceService = {
      getAll: vi.fn(),
      getExperienceById: mockGetExperienceById,
    };

    render(
      <MemoryRouter initialEntries={["/experiences/1"]}>
        <Routes>
          <Route
            path="/experiences/:id"
            element={<DetailedExperiencePage experienceService={mockService} />}
          />
        </Routes>
      </MemoryRouter>
    );

    expect(
      screen.getByText("Loading experience...")
    ).toBeInTheDocument();

    expect(mockGetExperienceById).toHaveBeenCalledWith(1);
  });

  it("should render the experience when it is successfully fetched", async () => {

    const mockGetExperienceById = vi
      .fn()
      .mockResolvedValue(fakeExperience);

    const mockService: ExperienceService = {
      getAll: vi.fn(),
      getExperienceById: mockGetExperienceById,
      getCommentsByExperienceId: vi.fn().mockResolvedValue([]),
    };

    render(
      <MemoryRouter initialEntries={["/experiences/1"]}>
        <Routes>
          <Route
            path="/experiences/:id"
            element={<DetailedExperiencePage experienceService={mockService} />}
          />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(
        screen.getByText("My Erasmus Experience")
      ).toBeInTheDocument();
    });

    expect(
      screen.getByText("John")
    ).toBeInTheDocument();

    expect(
      screen.getByText("This was an amazing Erasmus experience.")
    ).toBeInTheDocument();

    expect(
      screen.getByText(/2026-06-25/)
    ).toBeInTheDocument();

    expect(
      screen.getByText(/Madrid, Spain/)
    ).toBeInTheDocument();

    expect(
      screen.getByText("8.5")
    ).toBeInTheDocument();

    expect(
      screen.getByText("Culture")
    ).toBeInTheDocument();

    expect(
      screen.getByText("Gastronomy")
    ).toBeInTheDocument();

    expect(mockGetExperienceById).toHaveBeenCalledTimes(1);
    expect(mockGetExperienceById).toHaveBeenCalledWith(1);
  });

  it("should navigate to available-soon when the experience does not exist", async () => {

    const mockGetExperienceById = vi
      .fn()
      .mockResolvedValue(null);

    const mockService: ExperienceService = {
      getAll: vi.fn(),
      getExperienceById: mockGetExperienceById,
    };

    render(
      <MemoryRouter initialEntries={["/experiences/999"]}>
        <Routes>
          <Route
            path="/experiences/:id"
            element={<DetailedExperiencePage experienceService={mockService} />}
          />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith("/available-soon");
    });

    expect(mockGetExperienceById).toHaveBeenCalledWith(999);
    expect(mockNavigate).toHaveBeenCalledTimes(1);
  });

  it("should remain in loading state when fetching the experience fails", async () => {

    const error = new Error("Error fetching experience");

    const mockGetExperienceById = vi
      .fn()
      .mockRejectedValue(error);

    const mockService: ExperienceService = {
      getAll: vi.fn(),
      getExperienceById: mockGetExperienceById,
    };

    const consoleErrorSpy = vi
      .spyOn(console, "error")
      .mockImplementation(() => {});

    render(
      <MemoryRouter initialEntries={["/experiences/1"]}>
        <Routes>
          <Route
            path="/experiences/:id"
            element={<DetailedExperiencePage experienceService={mockService} />}
          />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    });

    expect(
      screen.getByText("Loading experience...")
    ).toBeInTheDocument();

    expect(mockNavigate).not.toHaveBeenCalled();

    consoleErrorSpy.mockRestore();
  });
});