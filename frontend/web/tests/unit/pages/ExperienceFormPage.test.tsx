import { beforeEach, describe, expect, it, vi } from "vitest";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { MemoryRouter } from "react-router-dom";
import ExperienceFormPage from "src/pages/ExperienceFormPage/ExperienceFormPage";
import { useUserStore } from "@shared/stores/userStore";

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

describe("ExperienceFormPage", () => {
  beforeEach(() => {
    useUserStore.setState({
      user: { id: 1, displayName: "Test User", email: "test@example.com" },
    });
    mockNavigate.mockClear();
    vi.restoreAllMocks();
  });

  it("renders the experience form and all required fields", async () => {
    const mockExperienceService = {
      getCategories: vi.fn().mockResolvedValue(["ART", "SPORT", "TRAVEL", "FOOD"]),
      postExperience: vi.fn(),
    };

    const mockCityService = {
      getAll: vi.fn().mockResolvedValue([
        { id: 1, name: "Madrid", country: "Spain" },
        { id: 2, name: "Paris", country: "France" },
      ]),
    };

    render(
      <MemoryRouter>
        <ExperienceFormPage
          experienceService={mockExperienceService as any}
          cityService={mockCityService as any}
        />
      </MemoryRouter>
    );

    expect(screen.getByRole("heading", { name: /new experience/i })).toBeInTheDocument();
    expect(screen.getByLabelText(/title/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/rating/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/location/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/date/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/experience description/i)).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /publish/i })).toBeInTheDocument();

    await waitFor(() => {
      expect(mockExperienceService.getCategories).toHaveBeenCalledTimes(1);
      expect(mockCityService.getAll).toHaveBeenCalledTimes(1);
    });
  });

  it("redirects to log in when there is no authenticated user", async () => {
    useUserStore.setState({ user: null });

    const mockExperienceService = {
      getCategories: vi.fn().mockResolvedValue(["ART"]),
      postExperience: vi.fn(),
    };

    const mockCityService = {
      getAll: vi.fn().mockResolvedValue([]),
    };

    render(
      <MemoryRouter>
        <ExperienceFormPage
          experienceService={mockExperienceService as any}
          cityService={mockCityService as any}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith("/log-in");
    });
  });

  it("renders the category and city options returned by the services", async () => {
    const mockExperienceService = {
      getCategories: vi.fn().mockResolvedValue(["ART_HISTORY", "SPORT", "TRAVEL"]),
      postExperience: vi.fn(),
    };

    const mockCityService = {
      getAll: vi.fn().mockResolvedValue([
        { id: 1, name: "Madrid", country: "Spain" },
        { id: 2, name: "Paris", country: "France" },
      ]),
    };

    render(
      <MemoryRouter>
        <ExperienceFormPage
          experienceService={mockExperienceService as any}
          cityService={mockCityService as any}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getByText("ART HISTORY")).toBeInTheDocument();
      expect(screen.getByText("SPORT")).toBeInTheDocument();
      expect(screen.getByText("TRAVEL")).toBeInTheDocument();
      expect(screen.getByRole("option", { name: /madrid, spain/i })).toBeInTheDocument();
      expect(screen.getByRole("option", { name: /paris, france/i })).toBeInTheDocument();
    });
  });

  it("alerts and stops submission when more than 3 categories are selected", async () => {
    const alertSpy = vi.spyOn(window, "alert").mockImplementation(() => {});
    const mockExperienceService = {
      getCategories: vi.fn().mockResolvedValue(["ART", "SPORT", "TRAVEL", "FOOD"]),
      postExperience: vi.fn(),
    };

    const mockCityService = {
      getAll: vi.fn().mockResolvedValue([{ id: 1, name: "Madrid", country: "Spain" }]),
    };

    render(
      <MemoryRouter>
        <ExperienceFormPage
          experienceService={mockExperienceService as any}
          cityService={mockCityService as any}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getAllByRole("checkbox")).toHaveLength(4);
    });

    fireEvent.change(screen.getByLabelText(/title/i), { target: { value: "Trip to Lisbon" } });
    fireEvent.change(screen.getByLabelText(/rating/i), { target: { value: "8" } });
    fireEvent.change(screen.getByLabelText(/location/i), { target: { value: "1" } });
    fireEvent.change(screen.getByLabelText(/date/i), { target: { value: "2026-08-15" } });
    fireEvent.change(screen.getByLabelText(/experience description/i), {
      target: { value: "A short but memorable trip." },
    });

    const checkboxes = screen.getAllByRole("checkbox");
    fireEvent.click(checkboxes[0]);
    fireEvent.click(checkboxes[1]);
    fireEvent.click(checkboxes[2]);
    fireEvent.click(checkboxes[3]);
    fireEvent.click(screen.getByRole("button", { name: /publish/i }));

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith("No more than 3 categories are allowed for an experience");
      expect(mockExperienceService.postExperience).not.toHaveBeenCalled();
    });
  });

  it("submits valid data and navigates to the available-soon page", async () => {
    const mockExperienceService = {
      getCategories: vi.fn().mockResolvedValue(["ART", "SPORT"]),
      postExperience: vi.fn().mockResolvedValue({}),
    };

    const mockCityService = {
      getAll: vi.fn().mockResolvedValue([{ id: 7, name: "Lisbon", country: "Portugal" }]),
    };

    render(
      <MemoryRouter>
        <ExperienceFormPage
          experienceService={mockExperienceService as any}
          cityService={mockCityService as any}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getAllByRole("checkbox")).toHaveLength(2);
    });

    fireEvent.change(screen.getByLabelText(/title/i), { target: { value: "Weekend in Lisbon" } });
    fireEvent.change(screen.getByLabelText(/rating/i), { target: { value: "9.5" } });
    fireEvent.change(screen.getByLabelText(/location/i), { target: { value: "7" } });
    fireEvent.change(screen.getByLabelText(/date/i), { target: { value: "2026-08-15" } });
    fireEvent.change(screen.getByLabelText(/experience description/i), {
      target: { value: "A beautiful trip through the city." },
    });

    fireEvent.click(screen.getAllByRole("checkbox")[0]);
    fireEvent.click(screen.getByRole("button", { name: /publish/i }));

    await waitFor(() => {
      expect(mockExperienceService.postExperience).toHaveBeenCalledWith({
        title: "Weekend in Lisbon",
        description: "A beautiful trip through the city.",
        date: "2026-08-15",
        rating: 9.5,
        cityId: 7,
        categories: ["ART"],
      });
      expect(mockNavigate).toHaveBeenCalledWith("/available-soon");
    });
  });

  it("alerts when the publish request fails", async () => {
    const alertSpy = vi.spyOn(window, "alert").mockImplementation(() => {});
    const consoleSpy = vi.spyOn(console, "log").mockImplementation(() => {});

    const mockExperienceService = {
      getCategories: vi.fn().mockResolvedValue(["ART"]),
      postExperience: vi.fn().mockRejectedValue(new Error("network issue")),
    };

    const mockCityService = {
      getAll: vi.fn().mockResolvedValue([{ id: 1, name: "Madrid", country: "Spain" }]),
    };

    render(
      <MemoryRouter>
        <ExperienceFormPage
          experienceService={mockExperienceService as any}
          cityService={mockCityService as any}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getAllByRole("checkbox")).toHaveLength(1);
    });

    fireEvent.change(screen.getByLabelText(/title/i), { target: { value: "Test title" } });
    fireEvent.change(screen.getByLabelText(/rating/i), { target: { value: "8" } });
    fireEvent.change(screen.getByLabelText(/location/i), { target: { value: "1" } });
    fireEvent.change(screen.getByLabelText(/date/i), { target: { value: "2026-09-01" } });
    fireEvent.change(screen.getByLabelText(/experience description/i), {
      target: { value: "This should fail." },
    });
    fireEvent.click(screen.getAllByRole("checkbox")[0]);
    fireEvent.click(screen.getByRole("button", { name: /publish/i }));

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith("Error while publishing your experience.");
      expect(consoleSpy).toHaveBeenCalledWith(expect.any(Error));
    });
  });
});