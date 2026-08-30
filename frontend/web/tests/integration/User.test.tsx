import { afterAll, afterEach, beforeAll, beforeEach, describe, expect, it, vi } from "vitest";
import "@testing-library/jest-dom";
import { cleanup, fireEvent, render, screen, waitFor } from "@testing-library/react";
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

  beforeEach(async () => {
    authenticatedUser = await authenticateUser("test@email.com");
    useUserStore.getState().setUser(authenticatedUser);
  });

  afterAll(() => {
    clearFetchAndUserStore();
    cleanup();
  });

  it("renders the authenticated user's profile with real API data", async () => {
    console.log("[User test] rendering UserPage with authenticated store state", authenticatedUser);
    render(
      <MemoryRouter initialEntries={["/profile"]}>
        <Routes>
          <Route path="/profile" element={<UserPage userService={testUserService} authService={testAuthService} />} />
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
          <Route path="/profile" element={<UserPage userService={testUserService} authService={testAuthService} />} />
          <Route path="/log-in" element={<div>Log in page</div>} />
        </Routes>
      </MemoryRouter>
    );

    fireEvent.click(await screen.findByRole("button", { name: /log out/i }));

    expect(await screen.findByText("Home page")).toBeInTheDocument();
  });

  it("redirects to city form", async () => {
    authenticatedUser = await authenticateUser("testadmin@email.com");
    render(
      <MemoryRouter initialEntries={["/profile"]}>
        <Routes>
          <Route path="/cities/new" element={<div>City Form</div>} />
          <Route path="/profile" element={<UserPage userService={testUserService} authService={testAuthService} />} />
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

    const experiences = await testUserService.getExperiences(authenticatedUser.id);

    console.log("Experiences:", experiences);

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
      expect(screen.queryByText("Loading experiences...")).not.toBeInTheDocument();
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

    authenticateUser("test@email.com")
  });

  it("cancels deleting the authenticated user", async () => {

    console.log("1 - antes de authenticateUser");
    console.log(useUserStore.getState());

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

  it("renders the authenticated user's experiences with real API data", async () => {

    console.log("1 - antes de authenticateUser");
    console.log(useUserStore.getState());

    authenticatedUser = await authenticateUser("exampleuser1@email.com");

    console.log("2 - después de authenticateUser");
    console.log(useUserStore.getState());

    const experiences = await testUserService.getExperiences(authenticatedUser.id);

    console.log("3 - después de getExperiences");
    console.log(experiences);

    render(
      <MemoryRouter initialEntries={["/profile"]}>
        <Routes>
          <Route
            path="/profile"
            element={
              <UserPage
                userService={testUserService}
                authService={testAuthService}
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

    expect(await screen.findByText("Experiences")).toBeInTheDocument();

    expect(screen.queryByText("Loading experiences...")).not.toBeInTheDocument(); 


    for (const experience of experiences) {
      expect(
        await screen.findByText(experience.title)
      ).toBeInTheDocument();
    }
  });

  it("loads the authenticated user's experiences when no userId is provided", async () => {
      authenticatedUser = await authenticateUser("exampleuser1@email.com")

    const experiences = await testUserService.getExperiences(
      authenticatedUser.id
    );

    render(
      <MemoryRouter initialEntries={["/profile"]}>
        <Routes>
          <Route
            path="/profile"
            element={
              <UserPage
                userService={testUserService}
                authService={testAuthService}
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

    expect(await screen.findByText("Experiences")).toBeInTheDocument();
    expect(screen.queryByText("Loading experiences...")).not.toBeInTheDocument(); 

    for (const experience of experiences) {
      expect(
        await screen.findByText(experience.title)
      ).toBeInTheDocument();
    }
  });
});