import { beforeAll, describe, expect, it } from "vitest";
import { render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { MemoryRouter } from "react-router-dom";

import { createApiClient } from "@shared/apiClient";
import { createAuthService } from "@shared/services/auth.service";
import { createUserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import type { LoginRequest } from "@shared/models/LoginRequest";

import { APIURL } from "src/config/env";
import UserPage from "src/pages/UserPage/UserPage";

const testAPI = createApiClient(APIURL);
const testAuthService = createAuthService(testAPI);
const testUserService = createUserService(testAPI);

describe("UserPage", () => {

  beforeAll(async () => {
    const loginRequest: LoginRequest = {
      username: "test@email.com",
      password: "password",
    };

    await testAuthService.logIn(loginRequest);

    const user = await testUserService.getUserInfo();

    useUserStore.setState({
      user,
    });
  });

  it("renders user information from API", async () => {
    render(
      <MemoryRouter>
        <UserPage
          authService={testAuthService}
          userService={testUserService}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getByText("Profile")).toBeInTheDocument();
      expect(screen.getByText(/Displayed Name:/i)).toBeInTheDocument();
      expect(screen.getByText(/Full Name:/i)).toBeInTheDocument();
      expect(screen.getByText(/Email:/i)).toBeInTheDocument();
      expect(screen.getByText(/Studying in:/i)).toBeInTheDocument();

      // Se han cargado los datos reales del usuario
      expect(screen.queryByText("test@email.com")).toBeInTheDocument();
    });
  });
});