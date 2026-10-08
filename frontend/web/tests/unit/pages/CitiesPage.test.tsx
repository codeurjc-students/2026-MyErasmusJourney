import CitiesPage from "@/pages/CItiesPage/CitiesPage";
import type { CitySimpleDTO } from "@shared/models/CitySimpleDTO";
import type { CityService } from "@shared/services/city.service";
import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import { vi, describe, beforeEach, it, expect } from "vitest";
import "@testing-library/jest-dom";
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

describe("CitiesPage", () => {

    beforeEach(() => {
        mockNavigate.mockClear();
        vi.restoreAllMocks();
    });

    it("should render the trending cities", async () => {
        const responseData: CitySimpleDTO[] = [
            {
                id: 1,
                name: "City 1",
                country: "Country 1",
            },
            {
                id: 2,
                name: "City 2",
                country: "Country 2",
            },
            {
                id: 3,
                name: "City 3",
                country: "Country 3",
            },
        ];

        const mockCityService: CityService = {
            addCity: vi.fn(),
            getAll: vi.fn(),
            getTrending: vi.fn().mockResolvedValue(responseData),
        };

        render(
            <MemoryRouter>
                <CitiesPage cityService={mockCityService} />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.queryAllByText("Cities")).toHaveLength(1);
            expect(screen.queryAllByRole("heading")).toHaveLength(2);
        });

        expect(await screen.findByText("City 1")).toBeInTheDocument();
        expect(screen.getByText("City 2")).toBeInTheDocument();
        expect(screen.getByText("City 3")).toBeInTheDocument();

        expect(mockCityService.getTrending).toHaveBeenCalledTimes(1);
    });

    it("should redirect to error page the trending cities fails because of internal error", async () => {
        const error = new ApiError(500, "Error fetching experience");

        const mockCityService: CityService = {
            addCity: vi.fn(),
            getAll: vi.fn(),
            getTrending: vi.fn().mockRejectedValue(error),
        };

        render(
            <MemoryRouter>
                <CitiesPage cityService={mockCityService} />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(mockNavigate).toHaveBeenCalledWith("/error");
        });

        expect(mockCityService.getTrending).toHaveBeenCalledTimes(1);
    });


})