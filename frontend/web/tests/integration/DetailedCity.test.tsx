import { describe, it, expect } from "vitest";
import "@testing-library/jest-dom";
import CityPage from "@/pages/CityPage/CityPage";
import type { CityDTO } from "@shared/models/CityDTO";
import { createCityService, type CityService } from "@shared/services/city.service";
import { render, waitFor, screen } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { ApiError } from "@shared/api/apiError";
import { APIURL } from "@/config/env";
import { createApiClient } from "@shared/api/apiClient";

const testAPI = createApiClient(APIURL);
const testCityService = createCityService(testAPI);


describe("CityPage", () => {

    it("should render city", async () => {
        const responseData: CityDTO = await testCityService.getCityById(2);

        render(
            <MemoryRouter initialEntries={["/cities/2"]}>
                <Routes>
                    <Route path="/cities/:id" element={<CityPage cityService={testCityService} />} />
                </Routes>
            </MemoryRouter>
        );

        expect(await screen.findByText(responseData.name)).toBeInTheDocument();
        expect(screen.getByText(responseData.averageRating.toFixed(1))).toBeInTheDocument();
        expect(screen.getByText(responseData.description)).toBeInTheDocument();

        responseData.experiences.forEach((experience) => {
            expect(screen.getByText(experience.title)).toBeInTheDocument();
        });
    });

    it("should navigate to error page when city does not exist", async () => {

        render(
            <MemoryRouter initialEntries={["/cities/0"]}>
                <Routes>
                    <Route path="/cities/:id" element={<CityPage cityService={testCityService} />} />
                    <Route path="/error" element={<div>Error Page</div>} />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.getByText("Error Page")).toBeInTheDocument();
        });
    });

    it("should navigate to error page when fetching city fails because of internal error", async () => {
        const cityServiceWithError: CityService = {
            addCity: testCityService.addCity,
            getAll: testCityService.getAll,
            getTrending: testCityService.getTrending,
            getCityById: async (id: number) => {
                const response = await testAPI.get("/tests/500");

                if (!response.ok) {
                    console.error("Error fetching experience with id: " + id)
                    throw new ApiError(response.status, await response.text());
                }

                return await response.json();
            },
        }
        render(
            <MemoryRouter initialEntries={["/cities/1"]}>
                <Routes>
                    <Route path="/cities/:id" element={<CityPage cityService={cityServiceWithError} />} />
                    <Route path="/error" element={<div>Error Page</div>} />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(
                screen.getByText("Error Page")
            ).toBeInTheDocument();
        });
    });
})