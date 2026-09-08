import { afterAll, beforeAll, beforeEach, describe, expect, it, vi } from "vitest";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { MemoryRouter, Route, Routes } from "react-router-dom";

import Comments from "src/components/Comments/Comments";

import { createApiClient } from "@shared/api/apiClient";
import { createExperienceService, type ExperienceService } from "@shared/services/experience.service";
import { useUserStore } from "@shared/stores/userStore";

import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";

import {
  authenticateUser,
  clearFetchAndUserStore,
} from "tests/testAuthentication";

import { APIURL } from "src/config/env";
import { ApiError } from "@shared/api/apiError";


const testAPI = createApiClient(APIURL);
const testExperienceService = createExperienceService(testAPI);


describe("Comments integration tests", () => {

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


  it("should load comments from the real API", async () => {

    render(
      <MemoryRouter>
        <Comments
          experienceService={testExperienceService}
          experienceId={1}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(
        screen.queryByText("Enjoyed this experience?")
      ).not.toBeInTheDocument();
    });

    expect(
      screen.getByPlaceholderText("Share your opinion...")
    ).toBeInTheDocument();
  });


  it("should post a comment using the real API", async () => {

    render(
      <MemoryRouter>
        <Comments
          experienceService={testExperienceService}
          experienceId={1}
        />
      </MemoryRouter>
    );

    const input = await screen.findByPlaceholderText(
      "Share your opinion..."
    );

    const comment = `Vitest integration comment ${Date.now()}`;

    fireEvent.change(input, {
      target: {
        value: comment,
      },
    });

    fireEvent.click(
      screen.getByRole("button", {
        name: "Send comment",
      })
    );

    await waitFor(() => {
      expect(
        screen.getByText(comment)
      ).toBeInTheDocument();
    });
  });


  it("should show login message when user is not authenticated", async () => {

    useUserStore.getState().setUser(null);

    render(
      <MemoryRouter>
        <Comments
          experienceService={testExperienceService}
          experienceId={1}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(
        screen.getByText(/Enjoyed this experience?/i)
      ).toBeInTheDocument();
    });

    expect(
      screen.getByRole("link", {
        name: /sign in/i,
      })
    ).toBeInTheDocument();

    expect(
      screen.queryByPlaceholderText(
        "Share your opinion..."
      )
    ).not.toBeInTheDocument();
  });

  it("should navigate to the error page when fetching comments fails with an internal server error", async () => {
    const testService: ExperienceService = {
      getAll: vi.fn(),

      getCommentsByExperienceId: async (experienceId: number) => {
        const response = await testAPI.get("/tests/500");

        if (!response.ok) {
          throw new ApiError(response.status, await response.text());
        }

        return await response.json();
      },

      postComment: vi.fn(),
    };

    render(
      <MemoryRouter initialEntries={["/experiences/1"]}>
        <Routes>
          <Route
            path="/experiences/:id"
            element={
              <Comments
                experienceService={testService}
                experienceId={1}
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