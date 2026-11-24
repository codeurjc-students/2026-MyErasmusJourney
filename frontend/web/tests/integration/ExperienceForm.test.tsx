import { afterAll, afterEach, beforeEach, describe, expect, it, vi } from "vitest";
import "@testing-library/jest-dom";
import { fireEvent, render, screen, waitFor, waitForElementToBeRemoved } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";

import { createApiClient } from "@shared/api/apiClient";
import { createCityService } from "@shared/services/city.service";
import { createExperienceService, type ExperienceService } from "@shared/services/experience.service";
import { useUserStore } from "@shared/stores/userStore";

import { APIURL } from "src/config/env";
import ExperienceFormPage from "src/pages/ExperienceFormPage/ExperienceFormPage";
import { authenticateUser, clearUserStore } from "tests/testAuthentication";
import DetailedExperiencePage from "src/pages/DetailedExperiencePage/DetailedExperiencePage";
import type { ExperienceFormDTO } from "@shared/models/ExperienceFormDTO";
import { ApiError } from "@shared/api/apiError";
import { toast } from "sonner";
import userEvent from "@testing-library/user-event";
import imageUrl from "../exampleMultimedia/experienceExampleImage.jpg?url";
import videoUrl from "../exampleMultimedia/experienceExampleVideo.mp4?url";

// Real services pointing to the real backend (integration test, nothing is mocked)
const testAPI = createApiClient(APIURL);
const testCityService = createCityService(testAPI);
const testExperienceService = createExperienceService(testAPI);

describe("ExperienceFormPage integration", () => {

  // Log in as a real test user before each test (also stores it in the user store)
  beforeEach(async () => {
    await authenticateUser("test@email.com");
  });

  afterEach(() => {
    vi.restoreAllMocks();
  });

  // Restore the original fetch and clear the user store
  afterAll(() => {
    clearUserStore();
  });

  async function renderExperienceForm() {
    render(
      <MemoryRouter initialEntries={["/experiences/new"]}>
        <Routes>
          <Route path="/experiences/new" element={<ExperienceFormPage experienceService={testExperienceService} cityService={testCityService} />}/>
          <Route path="/experiences/:id" element={<DetailedExperiencePage experienceService={testExperienceService}/>}/>
        </Routes>
      </MemoryRouter>
    );

    await screen.findAllByRole("status");

    await waitFor(() => {
      expect(screen.queryByRole("status")).not.toBeInTheDocument();
    });
  }

  it("renders the form with real categories and cities from the backend", async () => {
    await renderExperienceForm();

    expect(await screen.findByRole("heading", { name: /new experience/i })).toBeInTheDocument();
    expect(screen.getByLabelText(/title/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/rating/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/location/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/date/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/experience description/i)).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /publish/i })).toBeInTheDocument();

    const cities = await testCityService.getAll();

    await waitFor(() => {
      expect(screen.getByRole("checkbox", { name: /accommodation/i })).toBeInTheDocument();
      expect(screen.getByRole("option", { name: new RegExp(`${cities[0].name}, ${cities[0].country}`, "i"), })).toBeInTheDocument();
    });
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
    const warningSpy = vi
      .spyOn(toast, "warning")
      .mockImplementation(() => "mock-toast-id");
    const cities = await testCityService.getAll();

    await renderExperienceForm();

    await waitFor(() => {
      expect(screen.getByRole("checkbox", { name: /accommodation/i })).toBeInTheDocument();
    });

    fireEvent.change(screen.getByLabelText(/title/i), {
      target: { value: "Weekend in Porto" },
    });
    fireEvent.change(screen.getByLabelText(/rating/i), {
      target: { value: 8.7 },
    });

    const locationInput = screen.getByLabelText(/location/i) as HTMLSelectElement;

    fireEvent.change(locationInput, {
      target: { value: String(cities[0].id) },
    });

    expect(locationInput.value).toBe(String(cities[0].id));

    fireEvent.change(screen.getByLabelText(/date/i), {
      target: { value: "2026-09-10" },
    });
    fireEvent.change(screen.getByLabelText(/experience description/i), {
      target: { value: "A short trip with many activity categories." },
    });

    const checkboxList = screen.getAllByRole("checkbox");
    fireEvent.click(checkboxList[0]);
    fireEvent.click(checkboxList[1]);
    fireEvent.click(checkboxList[2]);
    fireEvent.click(checkboxList[3]);

    expect(screen.getByRole("button", { name: /publish/i })).toBeInTheDocument();

    fireEvent.click(screen.getByRole("button", { name: /publish/i }));

    const errorMessage = "No more than 3 categories are allowed for an experience";

    await waitFor(() => {
      expect(warningSpy).toHaveBeenCalledWith(errorMessage);
    });
  });

  it("shows an alert and blocks submission when date is not valid", async () => {
    const warning = vi
      .spyOn(toast, "warning")
      .mockImplementation(() => { return "mock-toast-id" });
    const cities = await testCityService.getAll();

    render(
      <MemoryRouter initialEntries={["/experiences/new"]}>
        <Routes>
          <Route
            path="/experiences/new"
            element={
              <ExperienceFormPage experienceService={testExperienceService} cityService={testCityService} />
            }
          />
        </Routes>
      </MemoryRouter>
    );

    const loading = await screen.findAllByText("Rendering the experience form...");
    await waitForElementToBeRemoved(loading);

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
      target: { value: "2028-09-10" },
    });
    fireEvent.change(screen.getByLabelText(/experience description/i), {
      target: { value: "A short trip with many activity categories." },
    });

    const checkboxList = screen.getAllByRole("checkbox");
    fireEvent.click(checkboxList[0]);
    fireEvent.click(checkboxList[1]);

    fireEvent.click(screen.getByRole("button", { name: /publish/i }));

    const errorMessage = "The experience cannot have a date after today";

    await waitFor(() => {
      expect(warning).toHaveBeenCalledWith(errorMessage);
    });
  });

  it("publishes a valid experience and navigates to experience detailed page", async () => {
    const cities = await testCityService.getAll();
    const title = `Real Experience`;

    await renderExperienceForm();

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

    const loading2 = await screen.findAllByRole("status");
    await waitForElementToBeRemoved(loading2);

    await waitFor(() => {
      expect(screen.getByText(title)).toBeInTheDocument();
    });
  });

  it("should navigate to the error page when publishing an experience fails with an internal server error", async () => {
    const cities = await testCityService.getAll();

    // Same service as the real one, but postExperience hits a test endpoint that always returns 500
    const experienceService: ExperienceService = {

      postExperience: async (experienceRequest: ExperienceFormDTO) => {
        const response = await testAPI.get("/tests/500");

        if (!response.ok) {
          console.error("Error posting the following experience")
          console.error(experienceRequest)
          throw new ApiError(response.status, await response.text());
        }

        return await response.json();
      },

      getCategories: testExperienceService.getCategories,
      getAll: testExperienceService.getAll,
      getCommentsByExperienceId: testExperienceService.getCommentsByExperienceId,
      getExperienceById: testExperienceService.getExperienceById,
      postComment: testExperienceService.postComment,
      deleteExperience: testExperienceService.deleteExperience,
      addMultimedia: testExperienceService.addMultimedia
    };

    render(
      <MemoryRouter initialEntries={["/experiences/new"]}>
        <Routes>
          <Route
            path="/experiences/new"
            element={
              <ExperienceFormPage
                experienceService={experienceService}
                cityService={testCityService}
              />
            }
          />
          <Route
            path="/error"
            element={<div>Error page</div>}
          />
        </Routes>
      </MemoryRouter>
    );

    const loading = await screen.findAllByText("Rendering the experience form...");
    await waitForElementToBeRemoved(loading);

    await waitFor(() => {
      expect(screen.getByRole("checkbox", { name: /studies/i })).toBeInTheDocument();
    });

    fireEvent.change(screen.getByLabelText(/title/i), {
      target: { value: "title" },
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

    expect(await screen.findByText("Error page")).toBeInTheDocument();
  })

  it("should create an experience and upload its multimedia successfully", async () => {
    const user = userEvent.setup();

    const imageResponse = await fetch(imageUrl);
    expect(imageResponse.ok).toBe(true);

    const imageBlob = await imageResponse.blob();

    const image = new File(
      [imageBlob],
      "experienceExampleImage.jpg",
      { type: "image/jpeg" }
    );

    const cities = await testCityService.getAll();
    const title = `Real Experience with Multimedia`;

    await renderExperienceForm();

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

    const multimediaInput =
      document.querySelector<HTMLInputElement>(
        'input[name="multimedia"]'
      );

    expect(multimediaInput).not.toBeNull();

    await user.upload(multimediaInput!, [image]);

    fireEvent.click(
      screen.getByRole("button", {
        name: /publish/i,
      })
    );

    const loading2 = await screen.findAllByRole("status");
    await waitForElementToBeRemoved(loading2);

    await waitFor(() => {
      expect(screen.getByText(title)).toBeInTheDocument();
    });
  });

  it("should warn user when any file exceeds maximum file size", async () => {

    const warningSpy = vi
      .spyOn(toast, "warning")
      .mockImplementation(() => "mock-toast-id");

    const user = userEvent.setup();

    // Real image used as the upload; wrapped in a jsdom File so the form input accepts it
    const videoResponse = await fetch(videoUrl);
    expect(videoResponse.ok).toBe(true);

    const videoBlob = await videoResponse.blob();

    const file = new File(
      [videoBlob],
      "experienceExampleVideo.mp4",
      {
        type: "video/mp4",
      }
    );

    const cities = await testCityService.getAll();
    const title = `Real Experience with Multimedia`;

    await renderExperienceForm();

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

    const multimediaInput =
      document.querySelector<HTMLInputElement>(
        'input[name="multimedia"]'
      );

    expect(multimediaInput).not.toBeNull();

    await user.upload(multimediaInput!, [file]);

    fireEvent.click(
      screen.getByRole("button", {
        name: /publish/i,
      })
    );

    expect(warningSpy).toHaveBeenCalledWith("File size exceeds the 16MB limit. Please upload smaller files.");

  });

})
