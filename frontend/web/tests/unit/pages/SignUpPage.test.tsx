import { describe, expect, it, vi } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { MemoryRouter, Routes, Route } from "react-router-dom";
import "@testing-library/jest-dom";
import SignUpPage from "../../../src/pages/SignUpPage/SignUpPage";
import type { UserService } from "@shared/services/user.service";
import { ApiError } from "@shared/api/apiError";

describe("SignUpPage", () => {
  it("should render the sign up form with all fields", () => {
    const mockSignUp = vi.fn();
    const mockService: UserService = {
      signUp: mockSignUp,
    };

    render(
      <MemoryRouter>
        <SignUpPage userService={mockService} />
      </MemoryRouter>
    );

    expect(screen.getByLabelText(/full name/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/public name/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/email/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/^password$/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/repeat password/i)).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /sign up/i })).toBeInTheDocument();
  });

  it("should successfully submit the form with valid data", async () => {
    const mockSignUp = vi.fn().mockResolvedValue({ id: 1, fullName: "John Doe", displayName: "johndoe", email: "john@example.com" });
    const mockService: UserService = {
      signUp: mockSignUp,
    };

    render(
      <MemoryRouter initialEntries={["/signup"]}>
        <Routes>
          <Route path="/signup" element={<SignUpPage userService={mockService} />} />
          <Route path="/log-in" element={<div>Log In</div>} />
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
      expect(mockSignUp).toHaveBeenCalledWith({
        fullName: "John Doe",
        displayName: "johndoe",
        email: "john@example.com",
        city:null,
        country:null,
        password: "password123",
        passwordConfirmation: "password123",
      });

      expect(screen.getByText("Log In")).toBeInTheDocument();
    });

  });

  it("should show alert when passwords do not match", async () => {
    const mockSignUp = vi.fn();
    const mockService: UserService = {
      signUp: mockSignUp,
    };

    global.alert = vi.fn();

    render(
      <MemoryRouter>
        <SignUpPage userService={mockService} />
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

    expect(global.alert).toHaveBeenCalledWith("Passwords do not match");
    expect(mockSignUp).not.toHaveBeenCalled();
  });

  it("should show error alert when sign up fails", async () => {
    const error = new ApiError(400,"Email already exists");
    
    const mockSignUp = vi.fn().mockRejectedValue(error);
    const mockService: UserService = {
      signUp: mockSignUp,
    };

    global.alert = vi.fn();
    global.console.log = vi.fn();

    delete (window as any).location;
    window.location = { href: "" } as Location;

    render(
      <MemoryRouter>
        <SignUpPage userService={mockService} />
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
      expect(global.alert).toHaveBeenCalledWith(
        expect.stringContaining("Error signing up")
      );
    });

    expect(window.location.href).not.toBe("/");
  });

  it("should collect form data correctly from all fields", async () => {
    const mockSignUp = vi.fn().mockResolvedValue({ id: 1, fullName: "Jane Smith", displayName: "janesmith", email: "jane@example.com" });
    const mockService: UserService = {
      signUp: mockSignUp,
    };

    delete (window as any).location;
    window.location = { href: "" } as Location;

    render(
      <MemoryRouter>
        <SignUpPage userService={mockService} />
      </MemoryRouter>
    );

    const fullNameInput = screen.getByLabelText(/full name/i) as HTMLInputElement;
    const displayNameInput = screen.getByLabelText(/public name/i) as HTMLInputElement;
    const emailInput = screen.getByLabelText(/email/i) as HTMLInputElement;
    const passwordInput = screen.getByLabelText(/^password$/i) as HTMLInputElement;
    const passwordConfirmInput = screen.getByLabelText(/repeat password/i) as HTMLInputElement;
    const submitButton = screen.getByRole("button", { name: /sign up/i });

    fireEvent.change(fullNameInput, { target: { value: "Jane Smith" } });
    fireEvent.change(displayNameInput, { target: { value: "janesmith" } });
    fireEvent.change(emailInput, { target: { value: "jane@example.com" } });
    fireEvent.change(passwordInput, { target: { value: "securePass456" } });
    fireEvent.change(passwordConfirmInput, { target: { value: "securePass456" } });

    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(mockSignUp).toHaveBeenCalledWith(
        expect.objectContaining({
          fullName: "Jane Smith",
          displayName: "janesmith",
          email: "jane@example.com",
          password: "securePass456",
          passwordConfirmation: "securePass456",
        })
      );
    });
  });

  it("should log success message on successful sign up", async () => {
    const mockSignUp = vi.fn().mockResolvedValue({ id: 1, fullName: "John Doe", displayName: "johndoe", email: "john@example.com" });
    const mockService: UserService = {
      signUp: mockSignUp,
    };

    global.console.log = vi.fn();

    delete (window as any).location;
    window.location = { href: "" } as Location;

    render(
      <MemoryRouter>
        <SignUpPage userService={mockService} />
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
      expect(global.console.log).toHaveBeenCalledWith("User signed up successfully");
    });
  });

  it("should submit city and country when they are provided", async () => {
    const mockSignUp = vi.fn().mockResolvedValue({
      id: 1,
      displayName: "johndoe",
      email: "john@example.com",
    });

    const mockService: UserService = {
      signUp: mockSignUp,
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
    };

    render(
      <MemoryRouter>
        <SignUpPage userService={mockService} />
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
      expect(mockSignUp).toHaveBeenCalledWith({
        fullName: "John Doe",
        displayName: "johndoe",
        city: "Madrid",
        country: "Spain",
        email: "john@example.com",
        password: "password123",
        passwordConfirmation: "password123",
      });
    });
  });

  it("should submit null city and country when they are left empty", async () => {
    const mockSignUp = vi.fn().mockResolvedValue({});

    const mockService: UserService = {
      signUp: mockSignUp,
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
    };

    render(
      <MemoryRouter>
        <SignUpPage userService={mockService} />
      </MemoryRouter>
    );

    fireEvent.change(screen.getByLabelText(/full name/i), {
      target: { value: "John Doe" },
    });

    fireEvent.change(screen.getByLabelText(/public name/i), {
      target: { value: "johndoe" },
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
      expect(mockSignUp).toHaveBeenCalledWith(
        expect.objectContaining({
          city: null,
          country: null,
        })
      );
    });
  });

  it("should trim city and country before submitting", async () => {
    const mockSignUp = vi.fn().mockResolvedValue({});

    const mockService: UserService = {
      signUp: mockSignUp,
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
    };

    render(
      <MemoryRouter>
        <SignUpPage userService={mockService} />
      </MemoryRouter>
    );

    fireEvent.change(screen.getByLabelText(/full name/i), {
      target: { value: "John Doe" },
    });

    fireEvent.change(screen.getByLabelText(/public name/i), {
      target: { value: "johndoe" },
    });

    fireEvent.change(screen.getByLabelText(/destination city/i), {
      target: { value: "   Madrid   " },
    });

    fireEvent.change(screen.getByLabelText(/destination country/i), {
      target: { value: "   Spain   " },
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
      expect(mockSignUp).toHaveBeenCalledWith(
        expect.objectContaining({
          city: "Madrid",
          country: "Spain",
        })
      );
    });
  });
});
