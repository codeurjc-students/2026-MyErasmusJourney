import { afterAll, beforeEach, describe, expect, it } from "vitest";
import "@testing-library/jest-dom";
import { cleanup, fireEvent, render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";

import { createApiClient } from "@shared/api/apiClient";
import { createUserService, type UserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";

import { APIURL } from "src/config/env";
import { authenticateUser, clearFetchAndUserStore } from "tests/testAuthentication";
import UserFormPage from "src/pages/UserFormPage/UserFormPage";
import type { UserDTO } from "@shared/models/UserDTO";
import UserPage from "src/pages/UserPage/UserPage";
import { createAuthService } from "@shared/services/auth.service";
import { ApiError } from "@shared/api/apiError";

const testAPI = createApiClient(APIURL);
const testUserService = createUserService(testAPI);
const testAuthService = createAuthService(testAPI);

describe("UserFormPage", () => {
    let authenticatedUser: UserSimpleDTO;

    beforeEach(async () => {
        authenticatedUser = await authenticateUser("exampleuser1@email.com");
        useUserStore.getState().setUser(authenticatedUser);
    });

    afterAll(() => {
        clearFetchAndUserStore();
        cleanup();
    });

    it("renders the authenticated user's profile in the form", async () => {
        const user: UserDTO = await testUserService.getUserById(authenticatedUser.id)

        render(
            <MemoryRouter initialEntries={["/user/update"]}>
                <Routes>
                    <Route path="/user/update" element={<UserFormPage userService={testUserService} mode="edit" />} />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.queryByText("Loading...")).not.toBeInTheDocument();
        });

        const fullNameInput = screen.getByLabelText(/full name/i) as HTMLInputElement;
        const displayNameInput = screen.getByLabelText(/public name/i) as HTMLInputElement;
        const studyLocationInput = screen.getByLabelText(/study location/i) as HTMLInputElement;
        const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
        const submitButton = screen.getByRole("button", { name: /save changes/i });

        expect(fullNameInput).toHaveValue(String(user.fullName));
        expect(displayNameInput).toHaveValue(String(user.displayName));
        expect(studyLocationInput).toHaveValue(String(user.studyLocation));
        expect(emailInput).toHaveValue(String(user.email));
        expect(submitButton).toBeInTheDocument();

    })

    it("renders the user page after success updating the user", async () => {
        const user: UserDTO = await testUserService.getUserById(authenticatedUser.id)

        render(
            <MemoryRouter initialEntries={["/user/update"]}>
                <Routes>
                    <Route path="/user/update" element={<UserFormPage userService={testUserService} mode="edit" />} />
                    <Route path="/account" element={<UserPage userService={testUserService} authService={testAuthService} />} />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.queryByText("Loading...")).not.toBeInTheDocument();
        });

        const fullNameInput = screen.getByLabelText(/full name/i) as HTMLInputElement;
        const displayNameInput = screen.getByLabelText(/public name/i) as HTMLInputElement;
        const studyLocationInput = screen.getByLabelText(/study location/i) as HTMLInputElement;
        const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
        const submitButton = screen.getByRole("button", { name: /save changes/i });

        fireEvent.change(fullNameInput, { target: { value: "New name" } });
        fireEvent.change(displayNameInput, { target: { value: String(user.displayName) } });
        fireEvent.change(emailInput, { target: { value: String(user.email) } });
        fireEvent.change(studyLocationInput, { target: { value: String(user.studyLocation) } });

        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(screen.getByText("Profile")).toBeInTheDocument();
            expect(screen.getByText(/New Name/i)).toBeInTheDocument();
        });
    })

    it("renders the error page after internal error while updating the user", async () => {
        const user: UserDTO = await testUserService.getUserById(authenticatedUser.id)

        const testService: UserService = {
            updateUser: async (id: number, body: UserDTO) => {
                const response = await testAPI.get("/tests/500");

                if (!response.ok) {
                    console.error("error updating user with id: " + id)
                    console.error(body)
                    throw new ApiError(response.status, await response.text());
                }

                return await response.json();
            },
            getUserById: testUserService.getUserById,
            getComments: testUserService.getComments,
            signUp: testUserService.signUp,
            getExperiences: testUserService.getExperiences,
            getUserInfo: testUserService.getUserInfo,
            deleteUserById: testUserService.deleteUserById
        }
        render(
            <MemoryRouter initialEntries={["/user/update"]}>
                <Routes>
                    <Route path="/user/update" element={<UserFormPage userService={testService} mode="edit" />} />
                    <Route path="/error" element={<><div>Error page</div></>} />
                </Routes>
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.queryByText("Loading...")).not.toBeInTheDocument();
        });

        const fullNameInput = screen.getByLabelText(/full name/i) as HTMLInputElement;
        const displayNameInput = screen.getByLabelText(/public name/i) as HTMLInputElement;
        const studyLocationInput = screen.getByLabelText(/study location/i) as HTMLInputElement;
        const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
        const submitButton = screen.getByRole("button", { name: /save changes/i });

        fireEvent.change(fullNameInput, { target: { value: "New name" } });
        fireEvent.change(displayNameInput, { target: { value: String(user.displayName) } });
        fireEvent.change(emailInput, { target: { value: String(user.email) } });
        fireEvent.change(studyLocationInput, { target: { value: String(user.studyLocation) } });

        fireEvent.click(submitButton);

        expect(await screen.findByText(/Error page/i)).toBeInTheDocument();

    })
})