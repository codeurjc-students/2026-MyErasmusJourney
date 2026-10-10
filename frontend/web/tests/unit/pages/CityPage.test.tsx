import { vi, describe, beforeEach, it, expect } from "vitest";
import "@testing-library/jest-dom";
import CityPage from "@/pages/CityPage/CityPage";
import type { CityDTO } from "@shared/models/CityDTO";
import type { CityService } from "@shared/services/city.service";
import { render, waitFor, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import { ApiError } from "@shared/api/apiError";

const mockNavigate = vi.fn();

vi.mock("react-router-dom", async () => {
    const actual = await vi.importActual<typeof import("react-router-dom")>(
        "react-router-dom"
    );

    return {
        ...actual,
        useNavigate: () => mockNavigate,
    };
});

describe("CityPage", () => {

    beforeEach(() => {
        mockNavigate.mockClear();
        vi.restoreAllMocks();
    });

    it("should render city", async () => {
        const responseData: CityDTO = {
            id: 1,
            name: "City 1",
            country: "Country 1",
            description: "Description 1",
            averageRating: 4.5,
            experiences: [{
                id: 5,
                title: "Experience 1",
                description: "Description 2",
                rating: 4.5,
                date: "2023-01-01",
                categories: ["Culture", "Studies"],
                cityName: "City 1",
                country: "Country 1",
                authorName: "user1",
            }],
        }

        const mockCityService: CityService = {
            addCity: vi.fn(),
            getAll: vi.fn(),
            getTrending: vi.fn(),
            getCityById: vi.fn().mockResolvedValue(responseData),
        };

        render(
            <MemoryRouter>
                <CityPage cityService={mockCityService} />
            </MemoryRouter>
        );

        expect(await screen.findByText("City 1")).toBeInTheDocument();
        expect(screen.getByText(/Country 1/i)).toBeInTheDocument();
        expect(screen.getByText(/Description 1/i)).toBeInTheDocument();
        expect(screen.queryAllByText("4.5")).toHaveLength(2);

        expect(mockCityService.getCityById).toHaveBeenCalledTimes(1);
    });

    it("should navigate to error page when city does not exist", async () => {
        const error = new ApiError(404, "Error fetching experience");

        const mockCityService: CityService = {
            addCity: vi.fn(),
            getAll: vi.fn(),
            getTrending: vi.fn(),
            getCityById: vi.fn().mockRejectedValue(error),
        };

        render(
            <MemoryRouter>
                <CityPage cityService={mockCityService} />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(mockNavigate).toHaveBeenCalledWith("/error");
        });

        expect(mockCityService.getCityById).toHaveBeenCalled();
        expect(mockNavigate).toHaveBeenCalledTimes(1);
    });

    it("should navigate to error page when fetching city fails because of internal error", async () => {
        const error = new ApiError(500, "Error fetching experience");

        const mockCityService: CityService = {
            addCity: vi.fn(),
            getAll: vi.fn(),
            getTrending: vi.fn(),
            getCityById: vi.fn().mockRejectedValue(error),
        };

        render(
            <MemoryRouter>
                <CityPage cityService={mockCityService} />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(mockNavigate).toHaveBeenCalledWith("/error");
        });

        expect(mockCityService.getCityById).toHaveBeenCalled();
        expect(mockNavigate).toHaveBeenCalledTimes(1);
    });
})