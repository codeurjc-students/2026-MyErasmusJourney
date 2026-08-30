import { afterAll, beforeAll, beforeEach, describe, expect, it } from "vitest";
import "@testing-library/jest-dom";
import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";

import { createApiClient } from "@shared/apiClient";
import { createUserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import type { UserSimpleDTO } from "@shared/models/UserSimpleDTO";

import { APIURL } from "src/config/env";
import App from "src/App";
import { authenticateUser, clearFetchAndUserStore } from "tests/testAuthentication";

const testAPI = createApiClient(APIURL);
const testUserService = createUserService(testAPI);

describe("App component", () => {
  let authenticatedUser: UserSimpleDTO;
  
  beforeAll(async () => {
      authenticatedUser = await authenticateUser("test@email.com");
    });
  
    beforeEach(() => {
      useUserStore.getState().setUser(authenticatedUser);
    });
  
    afterAll(() => {
      clearFetchAndUserStore();
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