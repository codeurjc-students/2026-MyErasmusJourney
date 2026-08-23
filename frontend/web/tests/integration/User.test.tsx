import { afterAll, beforeAll, beforeEach, describe, expect, it, vi } from "vitest";
import "@testing-library/jest-dom";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";

import { createApiClient } from "@shared/apiClient";
import { createAuthService } from "@shared/services/auth.service";
import { createUserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";

import { APIURL } from "src/config/env";
import UserPage from "src/pages/UserPage/UserPage";
import { authenticateUser, authenticateUserToDelete, clearFetchAndUserStore } from "tests/testAuthentication";

const testAPI = createApiClient(APIURL);
const testAuthService = createAuthService(testAPI);
const testUserService = createUserService(testAPI);

describe("UserPage", () => {
  let authenticatedUser: UserSimpleDTO;

  beforeAll(async() => {
    authenticatedUser = await authenticateUser(false);
  });

  beforeEach(() => {
    useUserStore.getState().setUser(authenticatedUser);
  });

  afterAll(() => {
    clearFetchAndUserStore();
  });

  it("renders the authenticated user's profile with real API data", async () => {
    console.log("[User test] rendering UserPage with authenticated store state", authenticatedUser);
    render(
      <MemoryRouter initialEntries={["/profile"]}>
        <Routes>
          <Route path="/profile" element={<UserPage userService={testUserService} authService={testAuthService}/>} />
          <Route path="/log-in" element={<div>Log in page</div>} />
        </Routes>
      </MemoryRouter>
    );

    expect(await screen.findByText("Profile")).toBeInTheDocument();
    expect(screen.getByText(/Displayed Name:/i)).toBeInTheDocument();
    expect(screen.getByText(/Email:/i)).toBeInTheDocument();

    if (authenticatedUser?.displayName) {
      expect(await screen.findByText(String(authenticatedUser.displayName))).toBeInTheDocument();
    }

    if (authenticatedUser?.email) {
      expect(await screen.findByText(String(authenticatedUser.email))).toBeInTheDocument();
    }
  });

  it("logs out and navigates back to the home page", async () => {
    render(
      <MemoryRouter initialEntries={["/profile"]}>
        <Routes>
          <Route path="/" element={<div>Home page</div>} />
          <Route path="/profile" element={<UserPage userService={testUserService} authService={testAuthService}/>} />
          <Route path="/log-in" element={<div>Log in page</div>} />
        </Routes>
      </MemoryRouter>
    );

    fireEvent.click(await screen.findByRole("button", { name: /log out/i }));

    expect(await screen.findByText("Home page")).toBeInTheDocument();
  });

  it("redirects to city form", async () => {
    authenticatedUser = await authenticateUser(true);
    render(
      <MemoryRouter initialEntries={["/profile"]}>
        <Routes>
          <Route path="/cities/new" element={<div>City Form</div>} />
          <Route path="/profile" element={<UserPage userService={testUserService} authService={testAuthService}/>} />
          <Route path="/log-in" element={<div>Log in page</div>} />
        </Routes>
      </MemoryRouter>
    );

    fireEvent.click(await screen.findByRole("button", { name: /add city/i }));

    expect(await screen.findByText("City Form")).toBeInTheDocument();
  });

  it("deletes the authenticated user successfully", async () => {
    authenticatedUser = await authenticateUserToDelete();
    useUserStore.getState().setUser(authenticatedUser);

    const confirmSpy = vi.spyOn(window, "confirm").mockReturnValue(true);

    render(
      <MemoryRouter initialEntries={["/profile"]}>
        <Routes>
          <Route
            path="/profile"
            element={
              <UserPage
                authService={testAuthService}
                userService={testUserService}
              />
            }
          />
          <Route
            path="/"
            element={<div>Home page</div>}
          />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getByText("Profile")).toBeInTheDocument();
    });

    fireEvent.click(
      screen.getByRole("button", { name: /delete profile/i })
    );

    expect(confirmSpy).toHaveBeenCalledWith(
      "This account is going to be deleted. This action cannot be undone. Are you certain?"
    );

    await waitFor(() => {
      expect(screen.getByText("Home page")).toBeInTheDocument();
    });

    confirmSpy.mockRestore();
  });

  it("cancels deleting the authenticated user", async () => {
    const confirmSpy = vi
      .spyOn(window, "confirm")
      .mockReturnValue(false);

    render(
      <MemoryRouter initialEntries={["/profile"]}>
        <Routes>
          <Route
            path="/profile"
            element={
              <UserPage
                authService={testAuthService}
                userService={testUserService}
              />
            }
          />
          <Route
            path="/log-in"
            element={<div>Log in page</div>}
          />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getByText("Profile")).toBeInTheDocument();
    });

    fireEvent.click(
      screen.getByRole("button", { name: /delete profile/i })
    );

    expect(confirmSpy).toHaveBeenCalledWith(
      "This account is going to be deleted. This action cannot be undone. Are you certain?"
    );

    expect(screen.getByText("Profile")).toBeInTheDocument();

    confirmSpy.mockRestore();
  });
});