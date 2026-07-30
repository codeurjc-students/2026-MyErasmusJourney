import { createApiClient } from "@shared/apiClient";
import { createUserService } from "@shared/services/user.service";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { APIURL } from "src/config/env";
import SignUpPage from "src/pages/SignUpPage/SignUpPage";
import { describe, expect, it, vi } from "vitest";
import LogInPage from "src/pages/LogInPage/LogInPage";

const testAPI = createApiClient(APIURL);
const testService = createUserService(testAPI);

describe("SignUpPage", () => {

  it("should successfully submit the form with valid data and render home page", async () => {
    render(
      <MemoryRouter initialEntries={["/signup"]}>
        <Routes>
          <Route path="/log-in" element={<LogInPage />} />
          <Route path="/signup" element={<SignUpPage userService={testService} />} />
        </Routes>
      </MemoryRouter>
    );

    const fullNameInput = screen.getByLabelText(/full name/i) as HTMLInputElement;
    const displayNameInput = screen.getByLabelText(/public name/i) as HTMLInputElement;
    const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
    const passwordInput = screen.getByLabelText(/^password$/i) as HTMLInputElement;
    const passwordConfirmInput = screen.getByLabelText(/repeat password/i) as HTMLInputElement;
    const submitButton = screen.getByRole("button", { name: /sign up/i });

    fireEvent.change(fullNameInput, { target: { value: "John Doe" } });
    fireEvent.change(displayNameInput, { target: { value: "johndoe" } });
    fireEvent.change(emailInput, { target: { value: "john@example.com" } });
    fireEvent.change(passwordInput, { target: { value: "password123" } });
    fireEvent.change(passwordConfirmInput, { target: { value: "password123" } });

    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByText(/log in/i)).toBeInTheDocument();
    });
  });
  
  it("should show alert when passwords do not match", async () => {

    const alertSpy = vi.spyOn(window, "alert").mockImplementation(() => {});
    vi.spyOn(window, "alert").mockImplementation(() => {});

    render(
      <MemoryRouter initialEntries={["/signup"]}>
        <Routes>
          <Route path="/log-in" element={<LogInPage />} />
          <Route path="/signup" element={<SignUpPage userService={testService} />} />
        </Routes>
      </MemoryRouter>
    );

    const fullNameInput = screen.getByLabelText(/full name/i) as HTMLInputElement;
    const displayNameInput = screen.getByLabelText(/public name/i) as HTMLInputElement;
    const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
    const passwordInput = screen.getByLabelText(/^password$/i) as HTMLInputElement;
    const passwordConfirmInput = screen.getByLabelText(/repeat password/i) as HTMLInputElement;
    const submitButton = screen.getByRole("button", { name: /sign up/i });

    fireEvent.change(fullNameInput, { target: { value: "John Doe" } });
    fireEvent.change(displayNameInput, { target: { value: "johndoe" } });
    fireEvent.change(emailInput, { target: { value: "john@example.com" } });
    fireEvent.change(passwordInput, { target: { value: "password123" } });
    fireEvent.change(passwordConfirmInput, { target: { value: "wrongpassword" } });

    fireEvent.click(submitButton);

    expect(alertSpy).toHaveBeenCalledWith("Passwords do not match");
  });

  it("should show error alert when sign up fails", async () => {

    const alertSpy = vi.spyOn(window, "alert").mockImplementation(() => {});
    vi.spyOn(window, "alert").mockImplementation(() => {});

    render(
      <MemoryRouter initialEntries={["/signup"]}>
        <Routes>
          <Route path="/log-in" element={<LogInPage />} />
          <Route path="/signup" element={<SignUpPage userService={testService} />} />
        </Routes>
      </MemoryRouter>
    );


    const fullNameInput = screen.getByLabelText(/full name/i) as HTMLInputElement;
    const displayNameInput = screen.getByLabelText(/public name/i) as HTMLInputElement;
    const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
    const passwordInput = screen.getByLabelText(/^password$/i) as HTMLInputElement;
    const passwordConfirmInput = screen.getByLabelText(/repeat password/i) as HTMLInputElement;
    const submitButton = screen.getByRole("button", { name: /sign up/i });

    fireEvent.change(fullNameInput, { target: { value: "John Doe" } });
    fireEvent.change(displayNameInput, { target: { value: "johndoe" } });
    fireEvent.change(emailInput, { target: { value: "john@example.com" } });
    fireEvent.change(passwordInput, { target: { value: "password123" } });
    fireEvent.change(passwordConfirmInput, { target: { value: "password123" } });

    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(alertSpy).toHaveBeenCalledWith(
        expect.stringContaining("Error signing up")
      );
    });

    expect(window.location.href).not.toBe("/log-in");
  });

  it("should successfully submit the form with city and country filled", async () => {
    render(
      <MemoryRouter initialEntries={["/signup"]}>
        <Routes>
          <Route path="/log-in" element={<LogInPage />} />
          <Route path="/signup" element={<SignUpPage userService={testService} />} />
        </Routes>
      </MemoryRouter>
    );

    fireEvent.change(screen.getByLabelText(/full name/i), {
      target: { value: "John Doe" },
    });

    fireEvent.change(screen.getByLabelText(/public name/i), {
      target: { value: "johndoe" },
    });

    fireEvent.change(screen.getByLabelText(/destination city/i), {
      target: { value: "Madrid" },
    });

    fireEvent.change(screen.getByLabelText(/destination country/i), {
      target: { value: "Spain" },
    });

    fireEvent.change(screen.getByLabelText(/email/i), {
      target: { value: "john@example.com" },
    });

    fireEvent.change(screen.getByLabelText(/^password$/i), {
      target: { value: "password123" },
    });

    fireEvent.change(screen.getByLabelText(/repeat password/i), {
      target: { value: "password123" },
    });

    fireEvent.click(screen.getByRole("button", { name: /sign up/i }));

    await waitFor(() => {
      expect(screen.getByText(/log in/i)).toBeInTheDocument();
    });
  });
});
