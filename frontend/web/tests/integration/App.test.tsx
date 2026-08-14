import { afterAll, beforeAll, beforeEach, describe, expect, it } from "vitest";
import "@testing-library/jest-dom";
import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import makeFetchCookie from "fetch-cookie";

import { createApiClient } from "@shared/apiClient";
import { createUserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import type { LoginRequest } from "@shared/models/LoginRequest";
import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";

import { APIURL } from "src/config/env";
import App from "src/App";
import { createAuthService } from "@shared/services/auth.service";

const testAPI = createApiClient(APIURL);
const testUserService = createUserService(testAPI);
const testAuthService = createAuthService(testAPI);


describe("App component", () => {
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

  it("should fetch the authenticated user and store it", async () => {

    render(
      <MemoryRouter initialEntries={["/"]}>
        <Routes>
          <Route path="*" element={<App userService={testUserService} />} />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      const user = useUserStore.getState().user;

      expect(user).not.toBeNull();
      expect(user?.id).toBe(authenticatedUser?.id);
      expect(user?.displayName).toBe(authenticatedUser?.displayName);
      expect(user?.email).toBe(authenticatedUser?.email);
    });
  });

  it("should render the application", async () => {

    render(
      <MemoryRouter initialEntries={["/"]}>
        <Routes>
          <Route path="*" element={<App userService={testUserService} />} />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getByRole("banner")).toBeInTheDocument();
    });
  });

  it("should clear the user when fetching user information fails", async () => {

    // Remove the authenticated user before rendering.
    useUserStore.setState({
      user: null,
    });

    render(
      <MemoryRouter initialEntries={["/"]}>
        <Routes>
          <Route path="*" element={<App userService={testUserService} />} />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(useUserStore.getState().user).toBeNull();
    });
  });
  
});