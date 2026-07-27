import { createApiClient } from "@shared/apiClient";
import { createUserService } from "@shared/services/user.service";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { APIURL } from "src/config/env";
import HomePage from "src/pages/HomePage/HomePage";
import { beforeAll, describe, expect, it, vi } from "vitest";
import LogInPage from "src/pages/LogInPage/LogInPage";
import { createAuthService } from "@shared/services/auth.service";

const testAPI = createApiClient(APIURL);
const testUserService = createUserService(testAPI);
const testAuthService = createAuthService(testAPI);

describe("LogInPage", () => {

    beforeAll(async ()=>{
      const userFormDTO = {
        fullName: "VitestTestUser",
        displayName: "VitestUser",
        email: "vitest@email.com",
        password: "password",
        passwordConfirmation: "password"
      }
      await testUserService.signUp(userFormDTO)
    })

  it("should successfully submit the form with valid data and render home page", async () => {
    render(
      <MemoryRouter initialEntries={["/log-in"]}>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/log-in" element={<LogInPage authService={testAuthService} userService={testUserService} />} />
        </Routes>
      </MemoryRouter>
    );

    const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
    const passwordInput = screen.getByLabelText(/^password$/i) as HTMLInputElement;
    const submitButton = screen.getByRole("button", { name: /sign up/i });

    fireEvent.change(emailInput, { target: { value: "vitest@email.com" } });
    fireEvent.change(passwordInput, { target: { value: "password" } });

    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByTestId("title")).toBeInTheDocument();
    });
  });
  
  it("should show alert when email is empty", async () => {

    const alertSpy = vi.spyOn(window, "alert").mockImplementation(() => {});
    vi.spyOn(window, "alert").mockImplementation(() => {});

    render(
      <MemoryRouter initialEntries={["/log-in"]}>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/log-in" element={<LogInPage authService={testAuthService} userService={testUserService} />} />
        </Routes>
      </MemoryRouter>
    );


    const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
    const passwordInput = screen.getByLabelText(/^password$/i) as HTMLInputElement;
    const submitButton = screen.getByRole("button", { name: /sign up/i });

    fireEvent.change(emailInput, { target: { value: "" } });
    fireEvent.change(passwordInput, { target: { value: "password" } });

    fireEvent.click(submitButton);

    expect(alertSpy).toHaveBeenCalledWith("Email missing");
  });

  it("should show error alert when log in fails", async () => {

    const alertSpy = vi.spyOn(window, "alert").mockImplementation(() => {});
    vi.spyOn(window, "alert").mockImplementation(() => {});

    render(
      <MemoryRouter initialEntries={["/log-in"]}>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/log-in" element={<LogInPage authService={testAuthService} userService={testUserService} />} />
        </Routes>
      </MemoryRouter>
    );

    const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
    const passwordInput = screen.getByLabelText(/^password$/i) as HTMLInputElement;
    const submitButton = screen.getByRole("button", { name: /sign up/i });
    
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
});
