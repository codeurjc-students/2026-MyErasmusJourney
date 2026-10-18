import { describe, beforeAll, beforeEach, afterAll, expect, it, vi } from "vitest";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
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
import type { ExperienceDTO } from "@shared/models/ExperienceDTO";


const testAPI = createApiClient(APIURL);
const testExperienceService = createExperienceService(testAPI);


describe("DetailedExperiencePage", () => {

  let authenticatedUser: UserSimpleDTO;
  let writeTextMock: ReturnType<typeof vi.fn>;
  let windowOpenMock: ReturnType<typeof vi.spyOn>;

  function mockShareFunctionalities() {
    vi.clearAllMocks();

    writeTextMock = vi.fn().mockResolvedValue(undefined);

    windowOpenMock = vi
      .spyOn(window, "open")
      .mockImplementation(() => null);

    Object.defineProperty(navigator, "clipboard", {
      configurable: true,
      value: {
        writeText: writeTextMock,
      },
    });

    Object.defineProperty(navigator, "share", {
      configurable: true,
      writable: true,
      value: undefined,
    });
  }


  async function openShareDialog() {
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

    fireEvent.click(
      screen.getByRole("button", {
        name: /share experience/i,
      })
    );
  }


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

    const experience: ExperienceDTO = await testExperienceService.getExperienceById(1);

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

    expect(screen.getByRole("button", { name: "Share experience" })).toBeInTheDocument();

    if (experience.multimedia != null) {
      const expectedVideo: number = experience.multimedia?.filter(
        (multimedia) => multimedia.contentType.startsWith("video/")
      ).length;

      const expectedImage: number = experience.multimedia?.filter(
        (multimedia) => multimedia.contentType.startsWith("image/")
      ).length;

      expect(screen.queryAllByRole("img", { name: "Multimedia" })).toHaveLength(expectedVideo);

      expect(screen.queryAllByTestId("experience-video")).toHaveLength(expectedImage);
    }

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
      addMultimedia: testExperienceService.addMultimedia,
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

  it("should display the current experience URL in the input", async () => {
    mockShareFunctionalities();

    await openShareDialog();

    const linkInput = screen.getByRole("textbox", {
      name: /experience link/i,
    });

    expect(linkInput).toHaveValue(window.location.href);
    expect(linkInput).toHaveAttribute("readonly");
  });

  it("should open WhatsApp with the encoded sharing message", async () => {
    mockShareFunctionalities();

    await openShareDialog();

    fireEvent.click(
      screen.getByRole("button", {
        name: /whatsapp/i,
      })
    );

    const message = [
      "Check out this experience on MyErasmusJourney!",
      "Discover all the details here:",
      window.location.href,
      "Ready to experience it yourself?",
    ].join("\n");

    expect(windowOpenMock).toHaveBeenCalledExactlyOnceWith(
      `https://wa.me/?text=${encodeURIComponent(message)}`,
      "_blank",
      "noopener,noreferrer"
    );
  });

  it("check the link is copied into the clipboard", async () => {
    mockShareFunctionalities();

    await openShareDialog();

    fireEvent.click(
      screen.getByRole("button", { name: /copy link/i })
    );

    await waitFor(() => {
      expect(writeTextMock).toHaveBeenCalledWith(
        window.location.href
      );
    });
  });

  it("should close the dialog when clicking Close", async () => {
    mockShareFunctionalities();

    await openShareDialog();

    expect(screen.getByRole("dialog")).toBeInTheDocument();

    fireEvent.click(
      document.querySelector<HTMLButtonElement>(".share-close-button")!
    );

    await waitFor(() => {
      expect(screen.queryByRole("dialog")).not.toBeInTheDocument();
    });
  });

});
