import { describe, beforeAll, beforeEach, afterAll, expect, it } from "vitest";
import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import "@testing-library/jest-dom";

import DetailedExperiencePage from "src/pages/DetailedExperiencePage/DetailedExperiencePage";

import { createApiClient } from "@shared/api/apiClient";
import { createExperienceService, type ExperienceService } from "@shared/services/experience.service";
import { useUserStore } from "@shared/stores/userStore";

import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";

import {
  authenticateUser,
  clearFetchAndUserStore
} from "tests/testAuthentication";

import { APIURL } from "src/config/env";
import { ApiError } from "@shared/api/apiError";


const testAPI = createApiClient(APIURL);
const testExperienceService = createExperienceService(testAPI);


describe("DetailedExperiencePage", () => {

  let authenticatedUser: UserSimpleDTO;

  beforeAll(async () => {
    authenticatedUser = await authenticateUser("test@email.com");
  });

  beforeEach(() => {
    useUserStore.getState().setUser(authenticatedUser);
  });

  afterAll(() => {
    clearFetchAndUserStore();
  });


  it("should load and display an existing experience", async () => {

    render(
      <MemoryRouter initialEntries={["/experiences/1"]}>
        <Routes>
          <Route
            path="/experiences/:id"
            element={
              <DetailedExperiencePage
                experienceService={testExperienceService}
              />
            }
          />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(
        screen.queryByText("Loading experience...")
      ).not.toBeInTheDocument();
    });

    expect(
      screen.getByRole("heading", { level: 3 })
    ).toBeInTheDocument();
  });


  it("should display the real experience information", async () => {

    render(
      <MemoryRouter initialEntries={["/experiences/1"]}>
        <Routes>
          <Route
            path="/experiences/:id"
            element={
              <DetailedExperiencePage
                experienceService={testExperienceService}
              />
            }
          />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(
        screen.queryByText("Loading experience...")
      ).not.toBeInTheDocument();
    });

    expect(screen.getByRole("heading", { level: 3 })).toBeInTheDocument();

    expect(
      screen.getByRole("button", { name: "Share experience" })
    ).toBeInTheDocument();

    expect(await screen.findByRole("heading", { level: 4 })).toBeInTheDocument();
  });


  it("should navigate to available-soon when the experience does not exist", async () => {

    render(
      <MemoryRouter initialEntries={["/experiences/999999999"]}>
        <Routes>
          <Route
            path="/experiences/:id"
            element={
              <DetailedExperiencePage
                experienceService={testExperienceService}
              />
            }
          />

          <Route
            path="/error"
            element={<div>Error Page</div>}
          />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(
        screen.getByText("Error Page")
      ).toBeInTheDocument();
    });
  });

  it("should navigate to the error page when fetching the experience fails with an internal server error", async () => {
    const testService: ExperienceService = {
      getAll: testExperienceService.getAll,
      getCategories: testExperienceService.getCategories,
      getCommentsByExperienceId: testExperienceService.getCommentsByExperienceId,
      getExperienceById: async (id: number) => {
        const response = await testAPI.get("/tests/500");

        if (!response.ok) {
          console.error("Error fetching experience with id: " + id)
          throw new ApiError(response.status, await response.text());
        }

        return await response.json();
      },
      postComment: testExperienceService.postComment,
      postExperience: testExperienceService.postExperience,
      deleteExperience: testExperienceService.deleteExperience
    };

    render(
      <MemoryRouter initialEntries={["/experiences/1"]}>
        <Routes>
          <Route
            path="/experiences/:id"
            element={
              <DetailedExperiencePage
                experienceService={testService}
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

    expect(
      await screen.findByText("Error page")
    ).toBeInTheDocument();
  });

});