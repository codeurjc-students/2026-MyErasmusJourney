import { afterAll, beforeAll, beforeEach, describe, expect, it } from "vitest";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { MemoryRouter } from "react-router-dom";

import Comments from "src/components/Comments/Comments";

import { createApiClient } from "@shared/apiClient";
import { createExperienceService } from "@shared/services/experience.service";
import { useUserStore } from "@shared/stores/userStore";

import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";

import {
  authenticateUser,
  clearFetchAndUserStore,
} from "tests/testAuthentication";

import { APIURL } from "src/config/env";


const testAPI = createApiClient(APIURL);
const testExperienceService = createExperienceService(testAPI);


describe("Comments integration tests", () => {

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

});