import { createApiClient } from "@shared/api/apiClient";
import { createUserService } from "@shared/services/user.service";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { APIURL } from "src/config/env";
import UserPage from "src/pages/UserPage/UserPage";
import { afterAll, describe, expect, it, vi } from "vitest";
import LogInPage from "src/pages/LogInPage/LogInPage";
import { createAuthService, type AuthService } from "@shared/services/auth.service";
import { useUserStore } from "@shared/stores/userStore";
import { ApiError } from "@shared/api/apiError";
import type { LoginRequest } from "@shared/models/LoginRequest";

const testAPI = createApiClient(APIURL);
const testUserService = createUserService(testAPI);
const testAuthService = createAuthService(testAPI);

describe("LogInPage", () => {

  it("should successfully submit the form with valid data and render home page", async () => {
    render(
      <MemoryRouter initialEntries={["/log-in"]}>
        <Routes>
          <Route path="/account" element={<UserPage />} />
          <Route path="/log-in" element={<LogInPage authService={testAuthService} userService={testUserService} />} />
        </Routes>
      </MemoryRouter>
    );

    const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
    const passwordInput = screen.getByLabelText(/^password$/i) as HTMLInputElement;
    const submitButton = screen.getByRole("button", { name: /sign in/i });

    fireEvent.change(emailInput, { target: { value: "test@email.com" } });
    fireEvent.change(passwordInput, { target: { value: "password" } });

    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByTestId("title")).toBeInTheDocument();
    });
  });

  it("should show alert when email is empty", async () => {

    const alertSpy = vi.spyOn(window, "alert").mockImplementation(() => { });
    vi.spyOn(window, "alert").mockImplementation(() => { });

    render(
      <MemoryRouter initialEntries={["/log-in"]}>
        <Routes>
          <Route path="/log-in" element={<LogInPage authService={testAuthService} userService={testUserService} />} />
        </Routes>
      </MemoryRouter>
    );


    const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
    const passwordInput = screen.getByLabelText(/^password$/i) as HTMLInputElement;
    const submitButton = screen.getByRole("button", { name: /sign in/i });

    fireEvent.change(emailInput, { target: { value: "" } });
    fireEvent.change(passwordInput, { target: { value: "password" } });

    fireEvent.click(submitButton);

    expect(alertSpy).toHaveBeenCalledWith("Email missing");
  });

  it("should show error alert when log in fails", async () => {

    const alertSpy = vi.spyOn(window, "alert").mockImplementation(() => { });
    vi.spyOn(window, "alert").mockImplementation(() => { });

    render(
      <MemoryRouter initialEntries={["/log-in"]}>
        <Routes>
          <Route path="/log-in" element={<LogInPage authService={testAuthService} userService={testUserService} />} />
        </Routes>
      </MemoryRouter>
    );

    const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
    const passwordInput = screen.getByLabelText(/^password$/i) as HTMLInputElement;
    const submitButton = screen.getByRole("button", { name: /sign in/i });

    fireEvent.change(emailInput, { target: { value: "vitest@email.com" } });
    fireEvent.change(passwordInput, { target: { value: "password123" } });

    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith(
        expect.stringContaining("Error logging in")
      );
    });

    expect(window.location.href).not.toBe("/");
  });

  it("should show redirect to error page when log in fails because of server internal error", async () => {
    const testService: AuthService = {
      logIn: async (loginRequest: LoginRequest) => {
        const response = await testAPI.get("/tests/500");

        if (!response.ok) {
          console.error("unable to log in with the following credentials")
          console.error(loginRequest)
          throw new ApiError(response.status, await response.text());
        }

        return await response.json();
      },
      logOut: testAuthService.logOut
    };

    render(
      <MemoryRouter initialEntries={["/log-in"]}>
        <Routes>
          <Route path="/log-in" element={<LogInPage authService={testService} userService={testUserService} />} />
          <Route path="/error" element={<div>Error page</div>} />
        </Routes>
      </MemoryRouter>
    );

    const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
    const passwordInput = screen.getByLabelText(/^password$/i) as HTMLInputElement;
    const submitButton = screen.getByRole("button", { name: /sign in/i });

    fireEvent.change(emailInput, { target: { value: "vitest@email.com" } });
    fireEvent.change(passwordInput, { target: { value: "password123" } });

    fireEvent.click(submitButton);

    expect(window.location.href).not.toBe("/");
    expect(
      await screen.findByText("Error page")
    ).toBeInTheDocument();
  });

  afterAll(() => {
    const setUser = useUserStore.getState().setUser;
    setUser(null);
  })
});