import { beforeEach, describe, expect, it, vi } from "vitest";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import "@testing-library/jest-dom";

import CityFormPage from "../../../src/pages/CityFormPage/CityFormPage";
import type { CityService } from "@shared/services/city.service";

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

describe("CityFormPage", () => {

  beforeEach(() => {
    mockNavigate.mockClear();
    vi.restoreAllMocks();
  });

  it("should render the city form with all fields", () => {

    const mockCityService: CityService = {
      addCity: vi.fn(),
    };

    render(
      <MemoryRouter>
        <CityFormPage cityService={mockCityService} />
      </MemoryRouter>
    );

    expect(screen.getByText("New City")).toBeInTheDocument();

    expect(screen.getByLabelText(/city name/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/country/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/city description/i)).toBeInTheDocument();

    expect(
      screen.getByRole("button", { name: /save city/i })
    ).toBeInTheDocument();

    expect(
      screen.getByAltText("City map preview")
    ).toBeInTheDocument();
  });

  it("should successfully submit the form with valid data", async () => {

    const mockAddCity = vi.fn().mockResolvedValue({});

    const mockCityService: CityService = {
      addCity: mockAddCity,
    };

    render(
      <MemoryRouter>
        <CityFormPage cityService={mockCityService} />
      </MemoryRouter>
    );

    fireEvent.change(screen.getByLabelText(/city name/i), {
      target: { value: "Madrid" },
    });

    fireEvent.change(screen.getByLabelText(/country/i), {
      target: { value: "Spain" },
    });

    fireEvent.change(screen.getByLabelText(/city description/i), {
      target: { value: "A beautiful city." },
    });

    fireEvent.click(
      screen.getByRole("button", { name: /save city/i })
    );

    await waitFor(() => {
      expect(mockAddCity).toHaveBeenCalledWith({
        name: "Madrid",
        country: "Spain",
        description: "A beautiful city.",
      });

      expect(mockAddCity).toHaveBeenCalledTimes(1);
    });
  });

  it("should navigate to available-soon after successfully adding the city", async () => {

    const mockAddCity = vi.fn().mockResolvedValue({});

    const mockCityService: CityService = {
      addCity: mockAddCity,
    };

    render(
      <MemoryRouter>
        <CityFormPage cityService={mockCityService} />
      </MemoryRouter>
    );

    fireEvent.change(screen.getByLabelText(/city name/i), {
      target: { value: "Madrid" },
    });

    fireEvent.change(screen.getByLabelText(/country/i), {
      target: { value: "Spain" },
    });

    fireEvent.change(screen.getByLabelText(/city description/i), {
      target: { value: "A beautiful city." },
    });

    fireEvent.click(
      screen.getByRole("button", { name: /save city/i })
    );

    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith("/available-soon");
    });

    expect(mockNavigate).toHaveBeenCalledTimes(1);
  });

  it("should not submit the form when required fields are empty", async () => {

    const mockAddCity = vi.fn();

    const mockCityService: CityService = {
      addCity: mockAddCity,
    };

    render(
      <MemoryRouter>
        <CityFormPage cityService={mockCityService} />
      </MemoryRouter>
    );

    fireEvent.click(
      screen.getByRole("button", { name: /save city/i })
    );

    await waitFor(() => {
      expect(mockAddCity).not.toHaveBeenCalled();
    });

    expect(mockNavigate).not.toHaveBeenCalled();
  });

  it("should show an alert when adding the city fails", async () => {

    const errorMessage = "City already exists";

    const mockAddCity = vi
      .fn()
      .mockRejectedValue(new Error(errorMessage));

    const mockCityService: CityService = {
      addCity: mockAddCity,
    };

    const alertSpy = vi
      .spyOn(window, "alert")
      .mockImplementation(() => {});

    render(
      <MemoryRouter>
        <CityFormPage cityService={mockCityService} />
      </MemoryRouter>
    );

    fireEvent.change(screen.getByLabelText(/city name/i), {
      target: { value: "Madrid" },
    });

    fireEvent.change(screen.getByLabelText(/country/i), {
      target: { value: "Spain" },
    });

    fireEvent.change(screen.getByLabelText(/city description/i), {
      target: { value: "A beautiful city." },
    });

    fireEvent.click(
      screen.getByRole("button", { name: /save city/i })
    );

    await waitFor(() => {
      expect(mockAddCity).toHaveBeenCalledWith({
        name: "Madrid",
        country: "Spain",
        description: "A beautiful city.",
      });

      expect(alertSpy).toHaveBeenCalled();
    });

    expect(mockNavigate).not.toHaveBeenCalled();
  });
});