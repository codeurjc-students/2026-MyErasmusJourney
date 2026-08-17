import { afterAll, beforeAll, beforeEach, describe, expect, it } from "vitest";
import "@testing-library/jest-dom";
import { fireEvent, render, screen } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";

import { createApiClient } from "@shared/apiClient";
import { createAuthService } from "@shared/services/auth.service";
import { createUserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";

import { APIURL } from "src/config/env";
import UserPage from "src/pages/UserPage/UserPage";
import { authenticateUser, clearFetchAndUserStore } from "tests/testAuthentication";

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
});