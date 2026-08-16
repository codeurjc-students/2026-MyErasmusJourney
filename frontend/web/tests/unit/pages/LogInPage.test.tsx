import { beforeEach, describe, expect, it, vi } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { MemoryRouter, Routes, Route } from "react-router-dom";
import "@testing-library/jest-dom";
import LogInPage from "../../../src/pages/LogInPage/LogInPage";
import type { AuthService } from "@shared/services/auth.service";
import type { UserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";

const mockNavigate = vi.fn();

vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual<typeof import("react-router-dom")>(
    "react-router-dom"
  );

  return {
    ...actual,
    useNavigate: () => mockNavigate,
  };
});

describe("Log In page", () => {

  beforeEach(() => {
    useUserStore.setState({
      user: null,
    });
    mockNavigate.mockClear();
  });

  const fillLoginForm = () => {
    fireEvent.change(screen.getByLabelText(/email/i), { target: { value: "john@example.com" } });
    fireEvent.change(screen.getByLabelText(/^password$/i), { target: { value: "password123" } });
  };

  it("should render the log in form with all fields", () => {
    const mockLogIn = vi.fn();
    const mockAuthService: AuthService = {
      logIn: mockLogIn,
    };

    const mockUserService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
    };

    render(
      <MemoryRouter>
        <LogInPage authService={mockAuthService} userService={mockUserService} />
      </MemoryRouter>
    );

    expect(screen.getByText(/log in/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/email/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/^password$/i)).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /sign in/i })).toBeInTheDocument();
  });

  it("should successfully submit the form with valid data and navigate to account", async () => {

    const mockLogIn = vi.fn().mockResolvedValue({});
    const mockGetUserInfo = vi.fn().mockResolvedValue({
      id: 1,
      displayName: "johndoe",
      email: "john@example.com"
    });

    const mockAuthService: AuthService = {
      logIn: mockLogIn,
    };

    const mockUserService: UserService = {
      signUp: vi.fn(),
      getUserInfo: mockGetUserInfo,
    };

    render(
      <MemoryRouter>
        <LogInPage
          authService={mockAuthService}
          userService={mockUserService}
        />
      </MemoryRouter>
    );

    fillLoginForm();

    fireEvent.click(
      screen.getByRole("button", { name: /sign in/i })
    );

    await waitFor(() => {
      expect(mockLogIn).toHaveBeenCalledWith({
        username: "john@example.com",
        password: "password123"
      });

      expect(mockGetUserInfo).toHaveBeenCalledTimes(1);

      expect(mockNavigate).toHaveBeenCalledWith("/account");
    });
  });

  it("should show alert when email is empty", async () => {
    const mockAuthService: AuthService = {
      logIn: vi.fn(),
    };

    const mockUserService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
    };

    global.alert = vi.fn();

    render(
      <MemoryRouter>
        <LogInPage authService={mockAuthService} userService={mockUserService} />
      </MemoryRouter>
    );

    fireEvent.change(screen.getByLabelText(/^password$/i), { target: { value: "password123" } });
    const form = screen.getByRole("button", { name: /sign in/i }).closest("form");
    fireEvent.submit(form!);

    await waitFor(() => {
      expect(global.alert).toHaveBeenCalledWith("Email missing");
      expect(mockAuthService.logIn).not.toHaveBeenCalled();
    });
  });

  it("should show alert when password is empty", async () => {
    const mockAuthService: AuthService = {
      logIn: vi.fn(),
    };

    const mockUserService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
    };

    global.alert = vi.fn();

    render(
      <MemoryRouter>
        <LogInPage authService={mockAuthService} userService={mockUserService} />
      </MemoryRouter>
    );

    fireEvent.change(screen.getByLabelText(/email/i), { target: { value: "john@example.com" } });
    const form = screen.getByRole("button", { name: /sign in/i }).closest("form");
    fireEvent.submit(form!);

    await waitFor(() => {
      expect(global.alert).toHaveBeenCalledWith("Passwords needed");
      expect(mockAuthService.logIn).not.toHaveBeenCalled();
    });
  });

  it("should show error alert when login fails", async () => {
    const errorMessage = "Invalid credentials";
    const mockLogIn = vi.fn().mockRejectedValue(new Error(errorMessage));

    const mockAuthService: AuthService = {
      logIn: mockLogIn,
    };

    const mockUserService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
    };

    global.alert = vi.fn();
    global.console.log = vi.fn();

    render(
      <MemoryRouter>
        <LogInPage authService={mockAuthService} userService={mockUserService} />
      </MemoryRouter>
    );

    fillLoginForm();
    fireEvent.click(screen.getByRole("button", { name: /sign in/i }));

    await waitFor(() => {
      expect(mockLogIn).toHaveBeenCalledWith({ username: "john@example.com", password: "password123" });
      expect(global.console.log).toHaveBeenCalledWith(expect.stringContaining("Error logging in"));
      expect(global.alert).toHaveBeenCalledWith(expect.stringContaining("Error logging in:"));
    });
  });

  it("should navigate to account if user is already logged in", async () => {
    useUserStore.setState({
      user: {
        id: 1,
        displayName: "johndoe",
        email: "john@example.com",
      },
    });

    const mockAuthService: AuthService = {
      logIn: vi.fn(),
    };

    const mockUserService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
    };

    render(
      <MemoryRouter>
        <LogInPage
          authService={mockAuthService}
          userService={mockUserService}
        />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith("/account");
    });

    expect(mockAuthService.logIn).not.toHaveBeenCalled();
  });
});
