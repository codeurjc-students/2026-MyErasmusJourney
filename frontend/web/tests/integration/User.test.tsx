import { afterAll, beforeAll, beforeEach, describe, expect, it } from "vitest";
import "@testing-library/jest-dom";
import { fireEvent, render, screen } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import makeFetchCookie from "fetch-cookie";

import { createApiClient } from "@shared/apiClient";
import { createAuthService } from "@shared/services/auth.service";
import { createUserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import type { LoginRequest } from "@shared/models/LoginRequest";
import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";

import { APIURL } from "src/config/env";
import UserPage from "src/pages/UserPage/UserPage";

const testAPI = createApiClient(APIURL);
const testAuthService = createAuthService(testAPI);
const testUserService = createUserService(testAPI);

describe("UserPage", () => {
  let authenticatedUser: UserSimpleDTO | null = null;
  const originalFetch = globalThis.fetch;

  beforeAll(async () => {
    const cookieAwareFetch = makeFetchCookie(globalThis.fetch);
    globalThis.fetch = cookieAwareFetch as typeof fetch;
    console.log("[User test] fetch wrapped with cookie-aware implementation");

    const setUser = useUserStore.getState().setUser;

    const loginRequest: LoginRequest = {
      username: "test@email.com",
      password: "password"
    };

    console.log("[User test] attempting login", { url: APIURL, payload: loginRequest });
    const loginResponse = await testAuthService.logIn(loginRequest);
    console.log("[User test] login response", loginResponse);

    const user = await testUserService.getUserInfo();
    console.log("[User test] user info response", user);
    authenticatedUser = {
      id: user.id,
      displayName: user.displayName,
      email: user.email
    };

    setUser(authenticatedUser);
  });

  beforeEach(() => {
    useUserStore.getState().setUser(authenticatedUser);
  });

  afterAll(() => {
    globalThis.fetch = originalFetch;
    useUserStore.getState().setUser(null);
  });

  it("renders the authenticated user's profile with real API data", async () => {
    console.log("[User test] rendering UserPage with authenticated store state", authenticatedUser);
    render(
      <MemoryRouter initialEntries={["/profile"]}>
        <Routes>
          <Route path="/profile" element={<UserPage />} />
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
          <Route path="/profile" element={<UserPage />} />
          <Route path="/log-in" element={<div>Log in page</div>} />
        </Routes>
      </MemoryRouter>
    );

    fireEvent.click(await screen.findByRole("button", { name: /log out/i }));

    expect(await screen.findByText("Home page")).toBeInTheDocument();
  });
});