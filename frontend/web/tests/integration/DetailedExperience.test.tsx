import { describe, beforeAll, beforeEach, afterAll, expect, it } from "vitest";
import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import "@testing-library/jest-dom";

import DetailedExperiencePage from "src/pages/DetailedExperiencePage/DetailedExperiencePage";

import { createApiClient } from "@shared/apiClient";
import { createExperienceService } from "@shared/services/experience.service";
import { useUserStore } from "@shared/stores/userStore";

import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";

import {
  authenticateUser,
  clearFetchAndUserStore
} from "tests/testAuthentication";

import { APIURL } from "src/config/env";


const testAPI = createApiClient(APIURL);
const testExperienceService = createExperienceService(testAPI);


describe("DetailedExperiencePage", () => {

  let authenticatedUser: UserSimpleDTO;

  beforeAll(async () => {
    authenticatedUser = await authenticateUser(false);
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
            path="/available-soon"
            element={<div>Available Soon Page</div>}
          />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(
        screen.getByText("Available Soon Page")
      ).toBeInTheDocument();
    });
  });

});