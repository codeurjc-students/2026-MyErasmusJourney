import { render, screen, waitFor } from "@testing-library/react";
import { describe, it, expect } from "vitest";
import { createExperienceService, type ExperienceService } from "@shared/services/experience.service";
import "@testing-library/jest-dom";
import { createApiClient } from "@shared/api/apiClient";
import { APIURL } from "src/config/env";
import ExperiencesPage from "src/pages/ExperiencesPage/ExperiencesPage";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { ApiError } from "@shared/api/apiError";

const testAPI = createApiClient(APIURL)
const testExperienceService = createExperienceService(testAPI);

describe("Experiences", () => {
    it("renders data from API", async () => {
        render(
            <MemoryRouter>
                <ExperiencesPage experienceService={testExperienceService} />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(
                screen.queryAllByText(/\d{4}-\d{2}-\d{2}/).length
            ).toBeGreaterThan(0);
        });
    });

    it("should navigate to the error page when fetching experiences fails with an internal server error", async () => {
        const testService: ExperienceService = {
            getAll: async (page?: number, size?: number) => {
                const response = await testAPI.get("/tests/500");

                if (!response.ok) {
                    console.error("Unable to fetch experiences with page " + page + " and size " + size)
                    throw new ApiError(response.status, await response.text());
                }

                return await response.json();
            },
            getCategories: testExperienceService.getCategories,
            getCommentsByExperienceId: testExperienceService.getCommentsByExperienceId,
            getExperienceById: testExperienceService.getExperienceById,
            postComment: testExperienceService.postComment,
            postExperience: testExperienceService.postExperience,
            deleteExperience: testExperienceService.deleteExperience
        };

        render(
            <MemoryRouter initialEntries={["/experiences"]}>
                <Routes>
                    <Route
                        path="/experiences"
                        element={
                            <ExperiencesPage
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
})