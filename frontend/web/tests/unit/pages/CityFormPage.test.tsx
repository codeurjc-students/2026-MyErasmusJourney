import { beforeEach, describe, expect, it, vi } from "vitest";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import "@testing-library/jest-dom";

import CityFormPage from "../../../src/pages/CityFormPage/CityFormPage";
import type { CityService } from "@shared/services/city.service";
import { ApiError } from "@shared/api/apiError";
import { toast } from "sonner";
import type { CityDTO } from "@shared/models/CityDTO";

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
      getAll: vi.fn(),
      getTrending: vi.fn(),
      getCityById: vi.fn(),
      geoLocate: vi.fn()
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
  });

  it("should successfully submit the form with valid data", async () => {

    const mockAddCity = vi.fn().mockResolvedValue({});

    const mockCityService: CityService = {
      addCity: mockAddCity,
      getAll: vi.fn(),
      getTrending: vi.fn(),
      getCityById: vi.fn(),
      geoLocate: vi.fn()
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
        longitude: null,
        latitude: null
      });

      expect(mockAddCity).toHaveBeenCalledTimes(1);
    });
  });

  it("should navigate to detailed city page after successfully adding the city", async () => {
    const newCity: CityDTO = { id: 1, name: "Madrid", country: "Spain", description: "A beautiful city", averageRating: 0, experiences: [], latitude: 40.4168, longitude: -3.7038 };

    const mockAddCity = vi.fn().mockResolvedValue(newCity);

    const mockCityService: CityService = {
      addCity: mockAddCity,
      getAll: vi.fn(),
      getTrending: vi.fn(),
      getCityById: vi.fn(),
      geoLocate: vi.fn()
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
      expect(mockNavigate).toHaveBeenCalledWith(`/cities/${newCity.id}`);
    });

    expect(mockNavigate).toHaveBeenCalledTimes(1);
  });

  it("should not submit the form when required fields are empty", async () => {

    const mockAddCity = vi.fn();

    const mockCityService: CityService = {
      addCity: mockAddCity,
      getAll: vi.fn(),
      getTrending: vi.fn(),
      getCityById: vi.fn(),
      geoLocate: vi.fn()
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

  it("should show a warning toast when adding the city fails", async () => {
    const errorMessage = "City already exists";

    const mockAddCity = vi
      .fn()
      .mockRejectedValue(new ApiError(400, errorMessage));

    const mockCityService: CityService = {
      addCity: mockAddCity,
      getAll: vi.fn(),
      getTrending: vi.fn(),
      getCityById: vi.fn(),
      geoLocate: vi.fn()
    };

    const warningSpy = vi
      .spyOn(toast, "warning")
      .mockImplementation(() => "mock-toast-id");

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
        longitude: null,
        latitude: null
      });

      expect(warningSpy).toHaveBeenCalledWith(errorMessage);
    });

    expect(mockNavigate).not.toHaveBeenCalled();
  });

  it("should redirect to error page when adding the city causes internal error", async () => {

    const errorMessage = "City already exists";

    const mockAddCity = vi
      .fn()
      .mockRejectedValue(new ApiError(500, errorMessage));

    const mockCityService: CityService = {
      addCity: mockAddCity,
      getAll: vi.fn(),
      getTrending: vi.fn(),
      getCityById: vi.fn(),
      geoLocate: vi.fn()
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
        longitude: null,
        latitude: null
      });
      expect(mockNavigate).toHaveBeenCalledWith("/error");
    });

  });

  it("should successfully submit the geolocation data", async () => {

    const mockGeolocateCity = vi.fn().mockResolvedValue({});

    const mockCityService: CityService = {
      addCity: vi.fn(),
      getAll: vi.fn(),
      getTrending: vi.fn(),
      getCityById: vi.fn(),
      geoLocate: mockGeolocateCity
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

    fireEvent.click(
      screen.getByRole("button", { name: /preview/i })
    );

    await waitFor(() => {
      expect(mockGeolocateCity).toHaveBeenCalledWith({
        name: "Madrid",
        country: "Spain",
      });

      expect(mockGeolocateCity).toHaveBeenCalledTimes(1);
    });
  });

  it("should redirect to error page when gelocating the city causes internal error", async () => {

    const errorMessage = "City couldn't be geolocated";

    const mockGeolocateCity = vi
      .fn()
      .mockRejectedValue(new ApiError(500, errorMessage));

    const mockCityService: CityService = {
      addCity: vi.fn(),
      getAll: vi.fn(),
      getTrending: vi.fn(),
      getCityById: vi.fn(),
      geoLocate: mockGeolocateCity
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

    fireEvent.click(
      screen.getByRole("button", { name: /preview/i })
    );

    await waitFor(() => {
      expect(mockGeolocateCity).toHaveBeenCalledWith({
        name: "Madrid",
        country: "Spain"
      });
      expect(mockNavigate).toHaveBeenCalledWith("/error");
    });

  });
});