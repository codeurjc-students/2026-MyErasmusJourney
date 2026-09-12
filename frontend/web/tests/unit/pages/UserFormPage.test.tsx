import { describe, expect, it, vi } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { MemoryRouter, Routes, Route } from "react-router-dom";
import "@testing-library/jest-dom";
import UserFormPage from "../../../src/pages/UserFormPage/UserFormPage";
import type { UserService } from "@shared/services/user.service";
import { ApiError } from "@shared/api/apiError";

const mockNavigate = vi.fn();

vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual<any>("react-router-dom");

  return {
    ...actual,
    useNavigate: () => mockNavigate,
  };
});

vi.mock("@shared/stores/userStore", () => ({
    useUserStore: () => ({
        user: {
            id: 1,
        },
    }),
}));

describe("SignUpPage", () => {
  it("should render the sign up form with all fields", () => {
    const mockSignUp = vi.fn();
    const mockService: UserService = {
      signUp: mockSignUp,
    };

    render(
      <MemoryRouter>
        <UserFormPage userService={mockService} mode="signup" />
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
          <Route path="/signup" element={<UserFormPage userService={mockService} mode="signup" />} />
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
        city: null,
        country: null,
        password: "password123",
        passwordConfirmation: "password123",
      });

      expect(mockNavigate).toHaveBeenCalledWith("/log-in");
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
        <UserFormPage userService={mockService} mode="signup" />
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
    const error = new ApiError(400, "Email already exists");

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
        <UserFormPage userService={mockService} mode="signup" />
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

  it("should redirect to error page when internal error while signing up", async () => {
    const error = new ApiError(500, "Internal Error");

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
        <UserFormPage userService={mockService} mode="signup" />
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
      expect(mockNavigate).toHaveBeenCalledWith("/error");
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
        <UserFormPage userService={mockService} mode="signup" />
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

  it("should redirect to log in on successful sign up", async () => {
    const mockSignUp = vi.fn().mockResolvedValue({ id: 1, fullName: "John Doe", displayName: "johndoe", email: "john@example.com" });
    const mockService: UserService = {
      signUp: mockSignUp,
    };

    delete (window as any).location;
    window.location = { href: "" } as Location;

    render(
      <MemoryRouter>
        <UserFormPage userService={mockService} mode="signup" />
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
      expect(mockNavigate).toHaveBeenCalledWith("/error");
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
        <UserFormPage userService={mockService} mode="signup" />
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
        <UserFormPage userService={mockService} mode="signup" />
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
        <UserFormPage userService={mockService} mode="signup" />
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

describe("UserFormPage", () => {

    it("should load the user information and render the edit form", async () => {

        const editTestService: UserService = {
            getUserById: async (id: number) => {
                return {
                    id,
                    fullName: "John Doe",
                    displayName: "johndoe",
                    email: "john@example.com",
                    studyLocation: "Madrid, Spain",
                    experiences: [],
                    comments: [],
                    roles: ["USER"],
                };
            },

            updateUser: async () => {
                return {
                    id: 1,
                    fullName: "John Doe",
                    displayName: "johndoe",
                    email: "john@example.com",
                    studyLocation: "Madrid, Spain",
                    experiences: [],
                    comments: [],
                    roles: ["USER"],
                };
            },
        };

        render(
            <MemoryRouter initialEntries={["/user/update"]}>
                <Routes>

                    <Route
                        path="/account"
                        element={<div>Account page</div>}
                    />

                    <Route
                        path="/user/update"
                        element={
                            <UserFormPage
                                userService={editTestService}
                                mode="edit"
                            />
                        }
                    />

                </Routes>
            </MemoryRouter>
        );

        expect(
            await screen.findByRole("heading", {
                name: /edit profile/i,
            })
        ).toBeInTheDocument();

        expect(
            screen.getByLabelText(/full name/i)
        ).toHaveValue("John Doe");

        expect(
            screen.getByLabelText(/public name/i)
        ).toHaveValue("johndoe");

        expect(
            screen.getByLabelText(/email/i)
        ).toHaveValue("john@example.com");

        expect(
            screen.getByLabelText(/study location/i)
        ).toHaveValue("Madrid, Spain");

        expect(
            screen.queryByLabelText(/^password$/i)
        ).not.toBeInTheDocument();

        expect(
            screen.queryByLabelText(/repeat password/i)
        ).not.toBeInTheDocument();

        expect(
            screen.queryByLabelText(/destination city/i)
        ).not.toBeInTheDocument();

        expect(
            screen.queryByLabelText(/destination country/i)
        ).not.toBeInTheDocument();
    });


    it("should successfully update the user with valid data", async () => {

        const updateUser = vi.fn().mockResolvedValue({
            id: 1,
            fullName: "Updated Name",
            displayName: "updatedName",
            email: "updated@example.com",
            studyLocation: "Paris, France",
            experiences: [],
            comments: [],
            roles: ["USER"],
        });

        const editTestService: UserService = {
            getUserById: async (id: number) => {
                return {
                    id,
                    fullName: "John Doe",
                    displayName: "johndoe",
                    email: "john@example.com",
                    studyLocation: "Madrid, Spain",
                    experiences: [],
                    comments: [],
                    roles: ["USER"],
                };
            },

            updateUser,
        };

        render(
            <MemoryRouter initialEntries={["/user/update"]}>
                <Routes>
                    <Route path="/user/update" element={ <UserFormPage userService={editTestService} mode="edit"/>}/>
                </Routes>
            </MemoryRouter>
        );

        const fullNameInput =
            await screen.findByLabelText(/full name/i);

        const displayNameInput =
            screen.getByLabelText(/public name/i);

        const emailInput =
            screen.getByLabelText(/email/i);

        const studyLocationInput =
            screen.getByLabelText(/study location/i);

        fireEvent.change(fullNameInput, {
            target: { value: "Updated Name" },
        });

        fireEvent.change(displayNameInput, {
            target: { value: "updatedName" },
        });

        fireEvent.change(emailInput, {
            target: { value: "updated@example.com" },
        });

        fireEvent.change(studyLocationInput, {
            target: { value: "Paris, France" },
        });

        fireEvent.click(
            screen.getByRole("button", {
                name: /save changes/i,
            })
        );

        await waitFor(() => {
            expect(updateUser).toHaveBeenCalledWith(
                1,
                expect.objectContaining({
                    id: 1,
                    fullName: "Updated Name",
                    displayName: "updatedName",
                    email: "updated@example.com",
                    studyLocation: "Paris, France",
                    experiences: [],
                    comments: [],
                    roles: ["USER"],
                })
            );
        });

        expect(mockNavigate).toHaveBeenCalledWith("/account");
    });


    it("should show an error alert when updating the user fails", async () => {

        const alertSpy = vi
            .spyOn(window, "alert")
            .mockImplementation(() => {});

        const editTestService: UserService = {
            getUserById: async (id: number) => {
                return {
                    id,
                    fullName: "John Doe",
                    displayName: "johndoe",
                    email: "john@example.com",
                    studyLocation: "Madrid, Spain",
                    experiences: [],
                    comments: [],
                    roles: ["USER"],
                };
            },

            updateUser: async () => {
                throw new ApiError(
                    400,
                    "Email already exists"
                );
            },
        };

        render(
            <MemoryRouter initialEntries={["/user/update"]}>
                <Routes>

                    <Route
                        path="/user/update"
                        element={
                            <UserFormPage
                                userService={editTestService}
                                mode="edit"
                            />
                        }
                    />

                </Routes>
            </MemoryRouter>
        );

        const emailInput =
            await screen.findByLabelText(/email/i);

        fireEvent.change(emailInput, {
            target: {
                value: "existing@example.com",
            },
        });

        fireEvent.click(
            screen.getByRole("button", {
                name: /save changes/i,
            })
        );

        await waitFor(() => {
            expect(alertSpy).toHaveBeenCalledWith(
                expect.stringContaining(
                    "Error updating user"
                )
            );
        });

        alertSpy.mockRestore();
    });


    it("should render the error page when updating the user fails because of a server error", async () => {

        const editTestService: UserService = {
            getUserById: async (id: number) => {
                return {
                    id,
                    fullName: "John Doe",
                    displayName: "johndoe",
                    email: "john@example.com",
                    studyLocation: "Madrid, Spain",
                    experiences: [],
                    comments: [],
                    roles: ["USER"],
                };
            },

            updateUser: async () => {
                throw new ApiError(
                    500,
                    "Internal server error"
                );
            },
        };

        render(
            <MemoryRouter initialEntries={["/user/update"]}>
                <Routes>
                    <Route path="/user/update" element={ <UserFormPage userService={editTestService} mode="edit"/>}/>
                </Routes>
            </MemoryRouter>
        );

        const fullNameInput =
            await screen.findByLabelText(/full name/i);

        fireEvent.change(fullNameInput, {
            target: {
                value: "Updated Name",
            },
        });

        fireEvent.click(
            screen.getByRole("button", {
                name: /save changes/i,
            })
        );

        expect(mockNavigate).toHaveBeenCalledWith("/error");
    });


    it("should render the error page when fetching user information fails because of a server error", async () => {

        const editTestService: UserService = {
            getUserById: async () => {
                throw new ApiError(
                    500,
                    "Internal server error"
                );
            },

            updateUser: vi.fn(),
        };

        render(
            <MemoryRouter initialEntries={["/user/update"]}>
                <Routes>
                    <Route path="/user/update" element={ <UserFormPage userService={editTestService} mode="edit"/>}/>
                </Routes>
            </MemoryRouter>
        );

        expect(mockNavigate).toHaveBeenCalledWith("/error");
    });

});