import CitiesPage from "@/pages/CItiesPage/CitiesPage";
import type { CitySimpleDTO } from "@shared/models/CitySimpleDTO";
import { createCityService, type CityService } from "@shared/services/city.service";
import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { vi, describe, beforeEach, it, expect } from "vitest";
import "@testing-library/jest-dom";
import { ApiError } from "@shared/api/apiError";
import { APIURL } from "@/config/env";
import { createApiClient } from "@shared/api/apiClient";


const testAPI = createApiClient(APIURL);
const testCityService = createCityService(testAPI);

describe("CitiesPage", () => {

    beforeEach(() => {
        vi.restoreAllMocks();
    });

    it("should render the trending cities correctly", async () => {

        const responseData: CitySimpleDTO[] = await testCityService.getTrending();

        render(
            <MemoryRouter>
                <CitiesPage cityService={testCityService} />
            </MemoryRouter>
        );

        for (const city of responseData) {
            await waitFor(() => {
                expect(screen.getByText(city.name)).toBeInTheDocument();
            });
        }
    });

    it("should redirect to error page the trending cities fails because of internal error", async () => {
        const mockCityService: CityService = {
            addCity: testCityService.addCity,
            getAll: testCityService.getAll,
            getCityById: testCityService.getCityById,
            getTrending: async () => {
                const response = await testAPI.get("/tests/500");

                if (!response.ok) {
                    throw new ApiError(response.status, await response.text());
                }

                return await response.json();
            }
        };

        render(
            <MemoryRouter initialEntries={["/cities"]}>
                <Routes>
                    <Route
                        path="/cities"
                        element={
                            <CitiesPage cityService={mockCityService} />
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