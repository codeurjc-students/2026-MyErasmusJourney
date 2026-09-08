import { describe, it, expect } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { MemoryRouter, Routes, Route } from "react-router-dom";

import ErrorPage from "../../src/pages/ErrorPage/ErrorPage";

describe("ErrorPage", () => {

    it("should navigate to the home page when clicking the button", async () => {
        render(
            <MemoryRouter initialEntries={["/error"]}>
                <Routes>
                    <Route
                        path="/error"
                        element={<ErrorPage />}
                    />
                    <Route
                        path="/"
                        element={<div>Home page</div>}
                    />
                </Routes>
            </MemoryRouter>
        );

        const image = screen.getByAltText("Error Image");

        expect(image).toBeTruthy();

        fireEvent.click(
            screen.getByRole("button", { name: /back to home page/i })
        );

        const homePage = await screen.findByText("Home page");
        expect(homePage).toBeTruthy();

        expect(screen.queryByAltText("Error Image")).toBeNull();
    });
});