import { afterAll, beforeAll, beforeEach, describe, expect, it, vi } from "vitest";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { MemoryRouter, Route, Routes } from "react-router-dom";

import CityFormPage from "src/pages/CityFormPage/CityFormPage";
import AvailableSoonPage from "src/pages/AvailableSoonPage/AvailableSoonPage";

import { createApiClient } from "@shared/api/apiClient";
import { createCityService, type CityService } from "@shared/services/city.service";
import { APIURL } from "src/config/env";

import { useUserStore } from "@shared/stores/userStore";
import { authenticateUser, clearFetchAndUserStore, } from "tests/testAuthentication";
import { toast } from "sonner";
import type { CityFormDTO } from "@shared/models/CityFormDTO";
import { ApiError } from "@shared/api/apiError";
import type { CityDTO } from "@shared/models/CityDTO";

const testAPI = createApiClient(APIURL);
const testCityService = createCityService(testAPI);

describe("CityFormPage", () => {

  beforeAll(async () => {
    await authenticateUser("testadmin@email.com");
  });

  beforeEach(() => {
    const user = useUserStore.getState().user;
    useUserStore.getState().setUser(user);
  });

  afterAll(() => {
    clearFetchAndUserStore();
  });


  it("should render the city form with all fields", () => {

    render(
      <MemoryRouter>
        <CityFormPage cityService={testCityService} />
      </MemoryRouter>
    );

    expect(screen.getByText("New City")).toBeInTheDocument();

    expect(
      screen.getByLabelText(/city name/i)
    ).toBeInTheDocument();

    expect(
      screen.getByLabelText(/country/i)
    ).toBeInTheDocument();

    expect(
      screen.getByLabelText(/city description/i)
    ).toBeInTheDocument();

    expect(
      screen.getByRole("button", { name: /save city/i })
    ).toBeInTheDocument();

    expect(
      screen.getByAltText("City map preview")
    ).toBeInTheDocument();
  });

  it("should successfully submit the form with valid data and navigate to available-soon", async () => {

    const cityName = `VitestCity-${Date.now()}`;

    render(
      <MemoryRouter initialEntries={["/city-form"]}>
        <Routes>
          <Route
            path="/city-form"
            element={<CityFormPage cityService={testCityService} />}
          />

          <Route
            path="/available-soon"
            element={<AvailableSoonPage />}
          />
        </Routes>
      </MemoryRouter>
    );

    fireEvent.change(
      screen.getByLabelText(/city name/i),
      {
        target: { value: cityName },
      }
    );

    fireEvent.change(
      screen.getByLabelText(/country/i),
      {
        target: { value: "Spain" },
      }
    );

    fireEvent.change(
      screen.getByLabelText(/city description/i),
      {
        target: { value: "A beautiful city created by an integration test." },
      }
    );

    fireEvent.click(
      screen.getByRole("button", { name: /save city/i })
    );

    await waitFor(() => {
      expect(
        screen.getByText(/not available yet/i)
      ).toBeInTheDocument();
    });
  });

  it("should not submit the form when required fields are empty", async () => {

    render(
      <MemoryRouter>
        <CityFormPage cityService={testCityService} />
      </MemoryRouter>
    );

    fireEvent.click(
      screen.getByRole("button", { name: /save city/i })
    );

    // HTML5 required impide que se ejecute onSubmit.
    expect(
      screen.getByLabelText(/city name/i)
    ).toBeInvalid();

    expect(
      screen.getByLabelText(/country/i)
    ).toBeInvalid();

    expect(
      screen.getByLabelText(/city description/i)
    ).toBeInvalid();
  });

  it("should show an alert when adding an already registered city", async () => {
    const alertSpy = vi
      .spyOn(toast, "warning")
      .mockImplementation(() => { return "mock-toast-id" });

    const city = await testCityService.getCityById(1);

    render(
      <MemoryRouter initialEntries={["/city-form"]}>
        <Routes>
          <Route
            path="/city-form"
            element={<CityFormPage cityService={testCityService} />}
          />
          <Route
            path="/available-soon"
            element={<div>Available Soon</div>}
          />
        </Routes>
      </MemoryRouter>
    );

    // Primera creación
    fireEvent.change(screen.getByLabelText(/city name/i), {
      target: { value: city.name },
    });

    fireEvent.change(screen.getByLabelText(/country/i), {
      target: { value: city.country },
    });

    fireEvent.change(screen.getByLabelText(/city description/i), {
      target: { value: "Trying to create the same city again." },
    });

    fireEvent.click(
      screen.getByRole("button", { name: /save city/i })
    );

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalled();
    });

    expect(alertSpy).toHaveBeenCalledWith(
      "City is already registered",
    );

    alertSpy.mockRestore();
  });

  it("should allow adding two cities with the same name but different countries", async () => {
    const cityName = `VitestCity-${Date.now()}`;

    // first city
    const firstRender = render(
      <MemoryRouter initialEntries={["/city-form"]}>
        <Routes>
          <Route
            path="/city-form"
            element={<CityFormPage cityService={testCityService} />}
          />
          <Route
            path="/available-soon"
            element={<div>Available Soon</div>}
          />
        </Routes>
      </MemoryRouter>
    );

    fireEvent.change(
      screen.getByLabelText(/city name/i),
      {
        target: { value: cityName },
      }
    );

    fireEvent.change(
      screen.getByLabelText(/country/i),
      {
        target: { value: "Spain" },
      }
    );

    fireEvent.change(
      screen.getByLabelText(/city description/i),
      {
        target: { value: "City in Spain." },
      }
    );

    fireEvent.click(
      screen.getByRole("button", { name: /save city/i })
    );

    await waitFor(() => {
      expect(screen.getByText("Available Soon")).toBeInTheDocument();
    });

    firstRender.unmount();

    // second city
    render(
      <MemoryRouter initialEntries={["/city-form"]}>
        <Routes>
          <Route
            path="/city-form"
            element={<CityFormPage cityService={testCityService} />}
          />
          <Route
            path="/available-soon"
            element={<div>Available Soon</div>}
          />
        </Routes>
      </MemoryRouter>
    );

    fireEvent.change(
      screen.getByLabelText(/city name/i),
      {
        target: { value: cityName },
      }
    );

    fireEvent.change(
      screen.getByLabelText(/country/i),
      {
        target: { value: "France" },
      }
    );

    fireEvent.change(
      screen.getByLabelText(/city description/i),
      {
        target: { value: "City in France." },
      }
    );

    fireEvent.click(
      screen.getByRole("button", { name: /save city/i })
    );

    await waitFor(() => {
      expect(screen.getByText("Available Soon")).toBeInTheDocument();
    });
  });

  it("should navigate to the error page when adding a city fails with an internal server error", async () => {
    const testService: CityService = {
      addCity: async (cityFormDTO: CityFormDTO) => {
        const response = await testAPI.get("/tests/500");

        if (!response.ok) {
          console.error("Error saving new city ")
          console.error(cityFormDTO)
          throw new ApiError(response.status, await response.text());
        }

        return await response.json() as CityDTO;
      },
      getAll: testCityService.getAll,
      getTrending: testCityService.getTrending,
      getCityById: testCityService.getCityById
    };

    render(
      <MemoryRouter initialEntries={["/city-form"]}>
        <Routes>
          <Route
            path="/city-form"
            element={
              <CityFormPage cityService={testService} />
            }
          />

          <Route
            path="/error"
            element={<div>Error page</div>}
          />
        </Routes>
      </MemoryRouter>
    );

    fireEvent.change(
      screen.getByLabelText(/city name/i),
      {
        target: { value: "Madrid" },
      }
    );

    fireEvent.change(
      screen.getByLabelText(/country/i),
      {
        target: { value: "Spain" },
      }
    );

    fireEvent.change(
      screen.getByLabelText(/city description/i),
      {
        target: {
          value: "A beautiful city created by an integration test.",
        },
      }
    );

    fireEvent.click(
      screen.getByRole("button", {
        name: /save city/i,
      })
    );

    expect(
      await screen.findByText("Error page")
    ).toBeInTheDocument();
  });
});