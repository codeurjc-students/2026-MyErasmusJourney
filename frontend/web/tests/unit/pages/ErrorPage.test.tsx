import { describe, it, expect, vi } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";

import ErrorPage from "../../../src/pages/ErrorPage/ErrorPage";

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

describe("ErrorPage", () => {

    it("should display the error image and home button", () => {
        render(
            <MemoryRouter>
                <ErrorPage />
            </MemoryRouter>
        );

        const image = screen.getByAltText("Error Image");

        expect(image).toBeTruthy();

        const button = screen.getByRole("button", {
            name: /back to home page/i
        });

        expect(button).toBeTruthy();
    });


    it("should navigate to home page when clicking the button", () => {

        render(
            <MemoryRouter>
                <ErrorPage />
            </MemoryRouter>
        );

        fireEvent.click(
            screen.getByRole("button", { name: /back to home page/i })
        );

        expect(navigateMock).toHaveBeenCalledTimes(1);
        expect(navigateMock).toHaveBeenCalledWith("/");
    });

});