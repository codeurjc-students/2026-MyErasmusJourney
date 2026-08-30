import { afterAll, afterEach, beforeAll, beforeEach, describe, expect, it, vi } from "vitest";
import "@testing-library/jest-dom";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";

import { createApiClient } from "@shared/apiClient";
import { createCityService } from "@shared/services/city.service";
import { createExperienceService } from "@shared/services/experience.service";
import { useUserStore } from "@shared/stores/userStore";

import { APIURL } from "src/config/env";
import AvailableSoonPage from "src/pages/AvailableSoonPage/AvailableSoonPage";
import ExperienceFormPage from "src/pages/ExperienceFormPage/ExperienceFormPage";
import { authenticateUser, clearFetchAndUserStore } from "tests/testAuthentication";
import DetailedExperiencePage from "src/pages/DetailedExperiencePage/DetailedExperiencePage";

const testAPI = createApiClient(APIURL);
const testCityService = createCityService(testAPI);
const testExperienceService = createExperienceService(testAPI);

describe("ExperienceFormPage integration", () => {

  beforeEach(async () => {
    await authenticateUser("test@email.com");
    const user = useUserStore.getState().user;
    useUserStore.getState().setUser(user);
  });

  afterEach(() => {
    vi.restoreAllMocks();
  });

  afterAll(() => {
    clearFetchAndUserStore();
  });

  it("renders the form with real categories and cities from the backend", async () => {
    render(
      <MemoryRouter initialEntries={["/experiences/new"]}>
        <Routes>
          <Route path="/experiences/new" element={<ExperienceFormPage experienceService={testExperienceService} cityService={testCityService}/>}/>
          <Route path="/log-in" element={<div>Log in page</div>} />
        </Routes>
      </MemoryRouter>
    );

    expect(await screen.findByRole("heading", { name: /new experience/i })).toBeInTheDocument();
    expect(screen.getByLabelText(/title/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/rating/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/location/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/date/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/experience description/i)).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /publish/i })).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByRole("checkbox", { name: /accommodation/i })).toBeInTheDocument();
    });

    const cities = await testCityService.getAll();

    expect(
      screen.getByRole("option", {
        name: new RegExp(`${cities[0].name}, ${cities[0].country}`, "i"),
      })
    ).toBeInTheDocument();
  });

  it("redirects to the login page when there is no authenticated user", async () => {
    useUserStore.getState().setUser(null);

    render(
      <MemoryRouter initialEntries={["/experiences/new"]}>
        <Routes>
          <Route
            path="/experiences/new"
            element={
              <ExperienceFormPage
                experienceService={testExperienceService}
                cityService={testCityService}
              />
            }
          />
          <Route path="/log-in" element={<div>Log in page</div>} />
        </Routes>
      </MemoryRouter>
    );

    expect(await screen.findByText("Log in page")).toBeInTheDocument();
  });

  it("shows an alert and blocks submission when more than 3 categories are selected", async () => {
    const alertSpy = vi.spyOn(window, "alert").mockImplementation(() => {});
    const cities = await testCityService.getAll();

    render(
      <MemoryRouter initialEntries={["/experiences/new"]}>
        <Routes>
          <Route
            path="/experiences/new"
            element={
              <ExperienceFormPage experienceService={testExperienceService} cityService={testCityService}/>
            }
          />
        </Routes>
      </MemoryRouter>
    );

    console.log("Checkpoint");

    await waitFor(() => {
      expect(screen.getByRole("checkbox", { name: /accommodation/i })).toBeInTheDocument();
    });

    fireEvent.change(screen.getByLabelText(/title/i), {
      target: { value: "Weekend in Porto" },
    });
    fireEvent.change(screen.getByLabelText(/rating/i), {
      target: { value: 8.7 },
    });
    fireEvent.change(screen.getByLabelText(/location/i), {
      target: { value: String(cities[0].id) },
    });
    fireEvent.change(screen.getByLabelText(/date/i), {
      target: { value: "2026-09-10" },
    });
    fireEvent.change(screen.getByLabelText(/experience description/i), {
      target: { value: "A short trip with many activity categories." },
    });

        console.log("Checkpoint 2");


    const checkboxList = screen.getAllByRole("checkbox");
    fireEvent.click(checkboxList[0]);
    fireEvent.click(checkboxList[1]);
    fireEvent.click(checkboxList[2]);
    fireEvent.click(checkboxList[3]);

    fireEvent.click(screen.getByRole("button", { name: /publish/i }));

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith(
        "No more than 3 categories are allowed for an experience"
      );
    });
  });

  it("publishes a valid experience and navigates to experience detailed page", async () => {
    const cities = await testCityService.getAll();
    const title = `Real Experience ${Date.now()}`;

    render(
      <MemoryRouter initialEntries={["/experiences/new"]}>
        <Routes>
          <Route
            path="/experiences/new"
            element={
              <ExperienceFormPage
                experienceService={testExperienceService}
                cityService={testCityService}
              />
            }
          />
          <Route path="/experiences/:id" element={<DetailedExperiencePage />} />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getByRole("checkbox", { name: /accommodation/i })).toBeInTheDocument();
    });

    fireEvent.change(screen.getByLabelText(/title/i), {
      target: { value: title },
    });
    fireEvent.change(screen.getByLabelText(/rating/i), {
      target: { value: "9.4" },
    });
    fireEvent.change(screen.getByLabelText(/location/i), {
      target: { value: String(cities[0].id) },
    });
    fireEvent.change(screen.getByLabelText(/date/i), {
      target: { value: "2026-08-15" },
    });
    fireEvent.change(screen.getByLabelText(/experience description/i), {
      target: { value: "A real experience created in the integration suite." },
    });

    fireEvent.click(screen.getByRole("checkbox", { name: /accommodation/i }));

    fireEvent.click(screen.getByRole("button", { name: /publish/i }));

    await waitFor(() => {
      expect(screen.getByText(title)).toBeInTheDocument();
    });
  });
});
