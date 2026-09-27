import { describe, it, expect, vi, beforeEach, afterEach } from "vitest";
import { render, screen, fireEvent, waitFor, } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";

import Filter from "../../../src/components/Filter/Filter";
import { ApiError } from "@shared/api/apiError";
import type { CitySimpleDTO } from "@shared/models/CitySimpleDTO";
import type { ExperienceFilters } from "@shared/models/ExperienceFilters";
import type { CityService } from "@shared/services/city.service";
import type { ExperienceService } from "@shared/services/experience.service";

const navigateMock = vi.fn();

vi.mock("react-router-dom", async () => {
    const actual = await vi.importActual<typeof import("react-router-dom")>(
        "react-router-dom"
    );

    return {
        ...actual,
        useNavigate: () => navigateMock,
    };
});

describe("Filter", () => {
    const cities: CitySimpleDTO[] = [
        {
            id: 1,
            name: "Madrid",
            country: "Spain",
        },
        {
            id: 2,
            name: "Paris",
            country: "France",
        },
    ];

    const categories = [
        "STUDIES",
        "ACCOMMODATION",
        "DOCUMENTATION",
        "PERSONAL_EXPERIENCE",
        "GASTRONOMY",
        "CULTURE",
        "SOCIAL_EVENTS",
        "TRANSPORTATION",
    ];

    let getAllMock: () => Promise<CitySimpleDTO[]>;
    let getCategoriesMock: () => Promise<string[]>;
    let setFiltersMock: (filters: ExperienceFilters) => void;

    const createServices = () => {
        getAllMock = vi.fn().mockResolvedValue(cities);
        getCategoriesMock = vi.fn().mockResolvedValue(categories);

        const cityService: CityService = {
            getAll: getAllMock,
            addCity: vi.fn()

        };

        const experienceService: ExperienceService = {
            getCategories: getCategoriesMock,
            getAll: vi.fn(),
            getCommentsByExperienceId: vi.fn(),
            getExperienceById: vi.fn(),
            postComment: vi.fn(),
            postExperience: vi.fn(),
            deleteExperience: vi.fn()
        };

        return {
            cityService,
            experienceService,
        };
    };

    beforeEach(() => {
        vi.clearAllMocks();
        setFiltersMock = vi.fn();
    });

    afterEach(() => {
        vi.restoreAllMocks();
    });

    it("should render the filter with cities and categories", async () => {
        const { cityService, experienceService } = createServices();

        render(
            <MemoryRouter>
                <Filter
                    setFilters={setFiltersMock}
                    cityService={cityService}
                    experienceService={experienceService}
                />
            </MemoryRouter>
        );

        expect(
            screen.getByRole("heading", { name: "Tailor Your Exploration" })
        ).toBeTruthy();

        expect(
            screen.getByRole("button", {
                name: "Find your next experiences",
            })
        ).toBeTruthy();

        await waitFor(() => {
            expect(getAllMock).toHaveBeenCalledTimes(1);
            expect(getCategoriesMock).toHaveBeenCalledTimes(1);
        });

        expect(screen.getByRole("option", { name: "All cities" })).toBeTruthy();
        expect(screen.getByRole("option", { name: "Madrid" })).toBeTruthy();
        expect(screen.getByRole("option", { name: "Paris" })).toBeTruthy();

        expect(
            screen.getByRole("checkbox", { name: "Studies" })
        ).toBeTruthy();

        expect(
            screen.getByRole("checkbox", { name: "Accommodation" })
        ).toBeTruthy();

        expect(
            screen.getByRole("checkbox", { name: "Documentation" })
        ).toBeTruthy();

        expect(
            screen.getByRole("checkbox", { name: "Personal Experience" })
        ).toBeTruthy();

        expect(
            screen.getByRole("checkbox", { name: "Gastronomy" })
        ).toBeTruthy();

        expect(
            screen.getByRole("checkbox", { name: "Culture" })
        ).toBeTruthy();

        expect(
            screen.getByRole("checkbox", { name: "Social Events" })
        ).toBeTruthy();

        expect(
            screen.getByRole("checkbox", { name: "Transportation" })
        ).toBeTruthy();
    });

    it("should collect the form data and call setFilters", async () => {
        const { cityService, experienceService } = createServices();

        render(
            <MemoryRouter>
                <Filter
                    setFilters={setFiltersMock}
                    cityService={cityService}
                    experienceService={experienceService}
                />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.getByRole("option", { name: "Madrid" })).toBeTruthy();
        });

        fireEvent.change(screen.getByLabelText("At least"), {
            target: { value: "3.5" },
        });

        fireEvent.change(screen.getByLabelText("No more than"), {
            target: { value: "9" },
        });

        fireEvent.change(screen.getByLabelText("From"), {
            target: { value: "2026-01-01" },
        });

        fireEvent.change(screen.getByLabelText("To"), {
            target: { value: "2026-06-30" },
        });

        fireEvent.change(screen.getByLabelText("City"), {
            target: { value: "Madrid" },
        });

        fireEvent.click(
            screen.getByRole("checkbox", { name: "Studies" })
        );

        fireEvent.click(
            screen.getByRole("checkbox", { name: "Culture" })
        );

        fireEvent.submit(
            screen.getByRole("button", {
                name: "Find your next experiences",
            })
        );

        await waitFor(() => {
            expect(setFiltersMock).toHaveBeenCalledTimes(1);
        });

        expect(setFiltersMock).toHaveBeenCalledWith({
            minRating: 3.5,
            maxRating: 9,
            from: "2026-01-01",
            to: "2026-06-30",
            cityName: "Madrid",
            page: 0,
            categories: ["STUDIES", "CULTURE"],
        });
    });

    it("should set undefined values when optional form fields are empty", async () => {
        const { cityService, experienceService } = createServices();

        render(
            <MemoryRouter>
                <Filter
                    setFilters={setFiltersMock}
                    cityService={cityService}
                    experienceService={experienceService}
                />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.getByRole("option", { name: "Madrid" })).toBeTruthy();
        });

        fireEvent.submit(
            screen.getByRole("button", {
                name: "Find your next experiences",
            })
        );

        await waitFor(() => {
            expect(setFiltersMock).toHaveBeenCalledTimes(1);
        });

        expect(setFiltersMock).toHaveBeenCalledWith({
            minRating: undefined,
            maxRating: undefined,
            from: undefined,
            to: undefined,
            cityName: undefined,
            page: 0,
            categories: [],
        });
    });

    it("should navigate to the error page when loading cities fails with a server error", async () => {
        const error = new ApiError(500, "Internal server error");

        getAllMock = vi.fn().mockRejectedValue(error);
        getCategoriesMock = vi.fn().mockResolvedValue(categories);

        const cityService: CityService = {
            getAll: getAllMock,
            addCity: vi.fn()

        };

        const experienceService: ExperienceService = {
            getCategories: getCategoriesMock,
            getAll: vi.fn(),
            getCommentsByExperienceId: vi.fn(),
            getExperienceById: vi.fn(),
            postComment: vi.fn(),
            postExperience: vi.fn(),
            deleteExperience: vi.fn()
        };


        const consoleErrorMock = vi
            .spyOn(console, "error")
            .mockImplementation(() => { });

        render(
            <MemoryRouter>
                <Filter
                    setFilters={setFiltersMock}
                    cityService={cityService}
                    experienceService={experienceService}
                />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(navigateMock).toHaveBeenCalledWith("/error");
        });

        expect(consoleErrorMock).toHaveBeenCalledWith(error);
    });

    it("should show an alert when loading cities fails with a non-server error", async () => {
        const error = new ApiError(400, "Bad request");

        getAllMock = vi.fn().mockRejectedValue(error);
        getCategoriesMock = vi.fn().mockResolvedValue(categories);

        const cityService: CityService = {
            getAll: getAllMock,
            addCity: vi.fn()

        };

        const experienceService: ExperienceService = {
            getCategories: getCategoriesMock,
            getAll: vi.fn(),
            getCommentsByExperienceId: vi.fn(),
            getExperienceById: vi.fn(),
            postComment: vi.fn(),
            postExperience: vi.fn(),
            deleteExperience: vi.fn()
        };


        const alertMock = vi
            .spyOn(window, "alert")
            .mockImplementation(() => { });

        render(
            <MemoryRouter>
                <Filter
                    setFilters={setFiltersMock}
                    cityService={cityService}
                    experienceService={experienceService}
                />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(alertMock).toHaveBeenCalledWith(error);
        });

        expect(navigateMock).not.toHaveBeenCalled();
    });

    it("should navigate to the error page when loading categories fails with a server error", async () => {
        const error = new ApiError(500, "Internal server error");

        getAllMock = vi.fn().mockResolvedValue(cities);
        getCategoriesMock = vi.fn().mockRejectedValue(error);

        const cityService: CityService = {
            getAll: getAllMock,
            addCity: vi.fn()

        };

        const experienceService: ExperienceService = {
            getCategories: getCategoriesMock,
            getAll: vi.fn(),
            getCommentsByExperienceId: vi.fn(),
            getExperienceById: vi.fn(),
            postComment: vi.fn(),
            postExperience: vi.fn(),
            deleteExperience: vi.fn()
        };


        const consoleErrorMock = vi
            .spyOn(console, "error")
            .mockImplementation(() => { });

        render(
            <MemoryRouter>
                <Filter
                    setFilters={setFiltersMock}
                    cityService={cityService}
                    experienceService={experienceService}
                />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(navigateMock).toHaveBeenCalledWith("/error");
        });

        expect(consoleErrorMock).toHaveBeenCalledWith(error);
    });

    it("should show an alert when loading categories fails with a non-server error", async () => {
        const error = new ApiError(400, "Bad request");

        getAllMock = vi.fn().mockResolvedValue(cities);
        getCategoriesMock = vi.fn().mockRejectedValue(error);

        const cityService: CityService = {
            getAll: getAllMock,
            addCity: vi.fn()

        };

        const experienceService: ExperienceService = {
            getCategories: getCategoriesMock,
            getAll: vi.fn(),
            getCommentsByExperienceId: vi.fn(),
            getExperienceById: vi.fn(),
            postComment: vi.fn(),
            postExperience: vi.fn(),
            deleteExperience: vi.fn()
        };


        const alertMock = vi
            .spyOn(window, "alert")
            .mockImplementation(() => { });

        render(
            <MemoryRouter>
                <Filter
                    setFilters={setFiltersMock}
                    cityService={cityService}
                    experienceService={experienceService}
                />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(alertMock).toHaveBeenCalledWith(error);
        });

        expect(navigateMock).not.toHaveBeenCalled();
    });

    it("should display the category name when the category is unknown", async () => {
        const { cityService } = createServices();

        getCategoriesMock = vi
            .fn()
            .mockResolvedValue(["UNKNOWN_CATEGORY"]);

        const experienceService: ExperienceService = {
            getCategories: getCategoriesMock,
            getAll: vi.fn(),
            getCommentsByExperienceId: vi.fn(),
            getExperienceById: vi.fn(),
            postComment: vi.fn(),
            postExperience: vi.fn(),
            deleteExperience: vi.fn()
        };


        render(
            <MemoryRouter>
                <Filter
                    setFilters={setFiltersMock}
                    cityService={cityService}
                    experienceService={experienceService}
                />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(
                screen.getByRole("checkbox", { name: "UNKNOWN_CATEGORY" })
            ).toBeTruthy();
        });
    });
});