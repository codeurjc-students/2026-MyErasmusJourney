import type { ExperienceFilters } from "@shared/models/ExperienceFilters";
import type { ExperiencePageDTO } from "@shared/models/ExperienceSimpleDTO";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import { describe, it, expect } from "vitest";
import { createExperienceService, type ExperienceService } from "@shared/services/experience.service";
import "@testing-library/jest-dom";
import { createApiClient } from "@shared/api/apiClient";
import { APIURL } from "src/config/env";
import ExperiencesPage from "src/pages/ExperiencesPage/ExperiencesPage";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { ApiError } from "@shared/api/apiError";
import { createCityService, type CityService } from "@shared/services/city.service";
import type { CitySimpleDTO } from "@shared/models/CitySimpleDTO";

const testAPI = createApiClient(APIURL)
const testExperienceService = createExperienceService(testAPI);
const testCityService = createCityService(testAPI)

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
            getAll: async (filters: ExperienceFilters) => {
                const response = await testAPI.get("/tests/500");

                if (!response.ok) {
                    console.error("Unable to fetch experiences with" + filters.toString())
                    throw new ApiError(response.status, await response.text());
                }

                return await response.json();
            },
            getCategories: testExperienceService.getCategories,
            getCommentsByExperienceId: testExperienceService.getCommentsByExperienceId,
            getExperienceById: testExperienceService.getExperienceById,
            postComment: testExperienceService.postComment,
            postExperience: testExperienceService.postExperience,
            deleteExperience: testExperienceService.deleteExperience,
            addMultimedia: testExperienceService.addMultimedia
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

    it("should render experiences, cities and categories from the API", async () => {

        render(
            <MemoryRouter initialEntries={["/experiences"]}>
                <Routes>
                    <Route path="/experiences" element={<ExperiencesPage experienceService={testExperienceService} cityService={testCityService} />} />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.queryAllByText(/\d{4}-\d{2}-\d{2}/).length).toBeGreaterThan(0);
        });

        expect(await screen.findByRole("option", { name: "All cities" })).toBeInTheDocument();

        expect(await screen.findByRole("checkbox", { name: "Studies" })).toBeInTheDocument();
    });

    it("should apply filters from the filter form", async () => {
        const requestedFilters: ExperienceFilters[] = [];

        const mockExperienceService: ExperienceService = {

            getAll: async (filters: ExperienceFilters) => {
                requestedFilters.push(filters);

                const response = await testAPI.get("/experiences/");

                if (!response.ok) {
                    throw new ApiError(
                        response.status,
                        await response.text()
                    );
                }

                return await response.json() as Promise<ExperiencePageDTO>;
            },
            getCategories: testExperienceService.getCategories,
            getCommentsByExperienceId: testExperienceService.getCommentsByExperienceId,
            getExperienceById: testExperienceService.getExperienceById,
            postComment: testExperienceService.postComment,
            postExperience: testExperienceService.postExperience,
            deleteExperience: testExperienceService.deleteExperience,
            addMultimedia: testExperienceService.addMultimedia
        };

        render(
            <MemoryRouter initialEntries={["/experiences"]}>
                <Routes>
                    <Route
                        path="/experiences"
                        element={
                            <ExperiencesPage experienceService={mockExperienceService} cityService={testCityService} />
                        }
                    />
                </Routes>
            </MemoryRouter>
        );

        await screen.findByRole("checkbox", { name: "Studies" });

        fireEvent.change(screen.getByLabelText("At least"), {
            target: { value: "5" },
        });

        fireEvent.change(screen.getByLabelText("No more than"), {
            target: { value: "10" },
        });

        fireEvent.click(
            screen.getByRole("checkbox", { name: "Studies" })
        );

        fireEvent.submit(
            screen.getByRole("button", {
                name: "Find your next experiences",
            })
        );

        await waitFor(() => {
            expect(requestedFilters.length).toBeGreaterThan(1);
        });

        const filters = requestedFilters.at(-1);

        expect(filters).toEqual({
            minRating: 5,
            maxRating: 10,
            from: undefined,
            to: undefined,
            cityName: undefined,
            page: 0,
            categories: ["Studies"],
        });
    });

    it("should apply the selected city filter", async () => {

        const cities: CitySimpleDTO[] = await testCityService.getAll()

        render(
            <MemoryRouter initialEntries={["/experiences"]}>
                <Routes>
                    <Route
                        path="/experiences"
                        element={
                            <ExperiencesPage
                                experienceService={testExperienceService}
                                cityService={testCityService}
                            />
                        }
                    />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.queryAllByText(/\d{4}-\d{2}-\d{2}/).length).toBeGreaterThan(0);
        });

        const citySelect = await screen.findByLabelText("City");

        fireEvent.change(citySelect, {
            target: { value: cities[0].name },
        });

        fireEvent.submit(
            screen.getByRole("button", {
                name: "Find your next experiences",
            })
        );

        await waitFor(() => {
            const experienceCards = document.querySelectorAll(
                '[id^="experience-"]'
            );

            expect(experienceCards.length).toBeGreaterThan(0);

            experienceCards.forEach((card) => {
                expect(card.textContent).toContain(cities[0].name);
            });
        });
    });

    it("should navigate to the error page when fetching cities fails with an internal server error", async () => {
        const mockCityService: CityService = {
            getAll: async () => {
                const response = await testAPI.get("/tests/500");

                if (!response.ok) {
                    throw new ApiError(
                        response.status,
                        await response.text()
                    );
                }

                return await response.json();
            },
            addCity: testCityService.addCity,
            getTrending: testCityService.getTrending,
            getCityById: testCityService.getCityById
        };

        render(
            <MemoryRouter initialEntries={["/experiences"]}>
                <Routes>
                    <Route
                        path="/experiences"
                        element={
                            <ExperiencesPage
                                experienceService={testExperienceService}
                                cityService={mockCityService}
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

    it("should navigate to the error page when fetching cities fails with an internal server error", async () => {

        const mockExperienceService: ExperienceService = {

            getAll: testExperienceService.getAll,
            getCategories: async () => {
                const response = await testAPI.get("/tests/500");

                if (!response.ok) {
                    throw new ApiError(
                        response.status,
                        await response.text()
                    );
                }

                return await response.json();
            },
            getCommentsByExperienceId: testExperienceService.getCommentsByExperienceId,
            getExperienceById: testExperienceService.getExperienceById,
            postComment: testExperienceService.postComment,
            postExperience: testExperienceService.postExperience,
            deleteExperience: testExperienceService.deleteExperience,
            addMultimedia: testExperienceService.addMultimedia
        };

        render(
            <MemoryRouter initialEntries={["/experiences"]}>
                <Routes>
                    <Route
                        path="/experiences"
                        element={
                            <ExperiencesPage
                                experienceService={mockExperienceService}
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

        expect(
            await screen.findByText("Error page")
        ).toBeInTheDocument();
    });
})