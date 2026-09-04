import { render, screen, waitFor, fireEvent } from "@testing-library/react";
import { describe, expect, it, vi, beforeEach } from "vitest";
import "@testing-library/jest-dom";
import UserPage from "src/pages/UserPage/UserPage";
import type { UserService } from "@shared/services/user.service";
import type { AuthService } from "@shared/services/auth.service";
import { useUserStore } from "@shared/stores/userStore";
import type { UserDTO } from "@shared/models/UserDTO";
import { ApiError } from "@shared/api/apiError";

// ---------- mocks ----------

const mockNavigate = vi.fn();

vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual<any>("react-router-dom");

  return {
    ...actual,
    useNavigate: () => mockNavigate,
  };
});

vi.mock("@shared/stores/userStore", () => ({
  useUserStore: vi.fn(),
}));

describe("UserPage", () => {

  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("renders user information", async () => {

    const fakeUser = {
      id: 1,
      displayName: "john",
      fullName: "John Doe",
      email: "john@test.com",
      studyLocation: "Madrid, Spain",
      roles: ["USER"]
    };

    const mockGetUser = vi.fn().mockResolvedValue(fakeUser);

    const mockService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: mockGetUser,
      getExperiences: vi.fn().mockResolvedValue([]),
      getComments: vi.fn().mockResolvedValue([]),
    };

    const mockAuth: AuthService = {
      logIn: vi.fn(),
      logOut: vi.fn()
    };

    (useUserStore as any).mockReturnValue({
      user: { id: 1 },
      setUser: vi.fn()
    });

    render(<UserPage authService={mockAuth} userService={mockService}/>);

    await waitFor(() => {
      expect(screen.getByText("john")).toBeInTheDocument();
      expect(screen.getByText("John Doe")).toBeInTheDocument();
      expect(screen.getByText("john@test.com")).toBeInTheDocument();
      expect(screen.getByText("Madrid, Spain")).toBeInTheDocument();
    });

    expect(mockGetUser).toHaveBeenCalledTimes(1);
    expect(mockGetUser).toHaveBeenCalledWith(1);
  });

  it("renders placeholder when study location is empty", async () => {

    const fakeUser = {
      id: 1,
      displayName: "john",
      fullName: "John Doe",
      email: "john@test.com",
      studyLocation: "",
      roles: ["USER"]
    };

    const mockGetUser = vi.fn().mockResolvedValue(fakeUser);

    const mockService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: mockGetUser,
      getExperiences: vi.fn().mockResolvedValue([]),
      getComments: vi.fn().mockResolvedValue([]),
    };

    (useUserStore as any).mockReturnValue({
      user: { id: 1 },
      setUser: vi.fn()
    });

    render(<UserPage userService={mockService}/>);

    await waitFor(() => {
      expect(
        screen.getByText("User has yet to complete this field")
      ).toBeInTheDocument();
    });

    expect(mockGetUser).toHaveBeenCalledTimes(1);
  });

  it("redirects to login when there is no logged user", async () => {

    const mockService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: vi.fn(),
      getExperiences: vi.fn().mockResolvedValue([]),
      getComments: vi.fn().mockResolvedValue([]),
    };

    (useUserStore as any).mockReturnValue({
      user: null,
      setUser: vi.fn()
    });

    render(<UserPage userService={mockService}/>);

    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith("/log-in");
    });
  });

  it("redirects to login when loading user fails", async () => {
    const error = new ApiError(400,"Mock");
    

    const consoleSpy = vi.spyOn(console, "error").mockImplementation(() => {});

    const mockGetUser = vi.fn().mockRejectedValue(error);

    const mockService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: mockGetUser,
      getExperiences: vi.fn().mockResolvedValue([]),
      getComments: vi.fn().mockResolvedValue([]),
    };

    (useUserStore as any).mockReturnValue({
      user: { id: 1 },
      setUser: vi.fn()
    });

    render(<UserPage userService={mockService}/>);

    await waitFor(() => {
      expect(consoleSpy).toHaveBeenCalled();
      expect(mockNavigate).toHaveBeenCalledWith("/log-in");
    });
  });

  it("logs out successfully", async () => {

    const fakeUser = {
      id: 1,
      displayName: "john",
      fullName: "John Doe",
      email: "john@test.com",
      studyLocation: "Madrid",
      roles: ["USER"]
    };

    const mockGetUser = vi.fn().mockResolvedValue(fakeUser);

    const mockLogOut = vi.fn();

    const setUser = vi.fn();

    const mockService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: mockGetUser,
      getExperiences: vi.fn().mockResolvedValue([]),
      getComments: vi.fn().mockResolvedValue([]),
    };

    const mockAuth: AuthService = {
      logIn: vi.fn(),
      logOut: mockLogOut
    };

    (useUserStore as any).mockReturnValue({
      user: { id: 1 },
      setUser
    });

    render(<UserPage authService={mockAuth} userService={mockService}/>);

    await waitFor(() => {
      expect(mockGetUser).toHaveBeenCalled();
    });

    fireEvent.click(screen.getByRole("button", { name: /log out/i }));

    expect(mockLogOut).toHaveBeenCalledTimes(1);
    expect(setUser).toHaveBeenCalledWith(null);
    expect(mockNavigate).toHaveBeenCalledWith("/");
  });

  it("renders all action buttons", async () => {

    const fakeUser = {
      id: 1,
      displayName: "john",
      fullName: "John Doe",
      email: "john@test.com",
      studyLocation: "Madrid",
      roles: ["USER"]
    };

    const mockService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: vi.fn().mockResolvedValue(fakeUser),
      getExperiences: vi.fn().mockResolvedValue([]),
      getComments: vi.fn().mockResolvedValue([]),
    };

    (useUserStore as any).mockReturnValue({
      user: { id: 1 },
      setUser: vi.fn()
    });

    render(<UserPage userService={mockService}/>);

    await waitFor(() => {
      expect(screen.getByRole("button", { name: /log out/i })).toBeInTheDocument();
      expect(screen.getByRole("button", { name: /edit profile/i })).toBeInTheDocument();
      expect(screen.getByRole("button", { name: /new experience/i })).toBeInTheDocument();
      expect(screen.getByRole("button", { name: /change picture/i })).toBeInTheDocument();
    });
  });

  it("renders all admin buttons", async () => {

    const fakeUser = {
      id: 1,
      displayName: "john",
      fullName: "John Doe",
      email: "john@test.com",
      studyLocation: "Madrid",
      roles: ["USER", "ADMIN"]
    };

    const mockService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: vi.fn().mockResolvedValue(fakeUser),
      getExperiences: vi.fn().mockResolvedValue([]),
      getComments: vi.fn().mockResolvedValue([]),
    };

    (useUserStore as any).mockReturnValue({
      user: { id: 1 },
      setUser: vi.fn()
    });

    render(<UserPage userService={mockService}/>);

    await waitFor(() => {
      expect(screen.getByRole("button", { name: /log out/i })).toBeInTheDocument();
      expect(screen.getByRole("button", { name: /edit profile/i })).toBeInTheDocument();
      expect(screen.getByRole("button", { name: /new experience/i })).toBeInTheDocument();
      expect(screen.getByRole("button", { name: /change picture/i })).toBeInTheDocument();
      expect(screen.getByRole("button", { name: /add city/i })).toBeInTheDocument();
    });
  });

  it("redirects to city form", async () => {

    const fakeUser = {
      id: 1,
      displayName: "john",
      fullName: "John Doe",
      email: "john@test.com",
      studyLocation: "Madrid",
      roles: ["USER", "ADMIN"]
    };

    const mockGetUser = vi.fn().mockResolvedValue(fakeUser);

    const mockLogOut = vi.fn();

    const setUser = vi.fn();

    const mockService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: mockGetUser,
      getExperiences: vi.fn().mockResolvedValue([]),
      getComments: vi.fn().mockResolvedValue([]),
    };

    const mockAuth: AuthService = {
      logIn: vi.fn(),
      logOut: mockLogOut
    };

    (useUserStore as any).mockReturnValue({
      user: { id: 1 },
      setUser
    });

    render(<UserPage authService={mockAuth} userService={mockService}/>);

    await waitFor(() => {
      expect(mockGetUser).toHaveBeenCalled();
    });

    fireEvent.click(screen.getByRole("button", { name: /add city/i }));

    expect(mockNavigate).toHaveBeenCalledWith("/cities/new");
  });

  it("deletes user and calls logout ", async () => {

    const confirmSpy = vi.spyOn(window, "confirm").mockReturnValue(true);

    const fakeUser = {
      id: 1,
      displayName: "john",
      fullName: "John Doe",
      email: "john@test.com",
      studyLocation: "Madrid",
      roles: ["USER", "ADMIN"]
    };

    const fakeUserDTO: UserDTO = {
        id: 1,
        displayName: "test",
        fullName: "userTest",
        email: "test@email.com",
        studyLocation: "To be filled",
        roles: ["USER"],
        experiences:[]

    }

    const mockGetUser = vi.fn().mockResolvedValue(fakeUser);

    const mockLogOut = vi.fn();

    const setUser = vi.fn();

    const mockDeleteUserById = vi.fn().mockResolvedValue(fakeUserDTO);


    const mockService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: mockGetUser,
      deleteUserById: mockDeleteUserById,
      getExperiences: vi.fn().mockResolvedValue([]),
      getComments: vi.fn().mockResolvedValue([]),
    };

    const mockAuth: AuthService = {
      logIn: vi.fn(),
      logOut: mockLogOut
    };

    (useUserStore as any).mockReturnValue({
      user: { id: 1 },
      setUser
    });

    render(<UserPage authService={mockAuth} userService={mockService}/>);

    await waitFor(() => {
      expect(mockGetUser).toHaveBeenCalled();
    });

    fireEvent.click(screen.getByRole("button", { name: /delete profile/i }));

    expect(confirmSpy).toHaveBeenCalledWith("This account is going to be deleted. This action cannot be undone. Are you certain?");
    expect(mockDeleteUserById).toHaveBeenCalledTimes(1);
  });

  it("cancels delete user ", async () => {

    const confirmSpy = vi.spyOn(window, "confirm").mockReturnValue(false);

    const fakeUser = {
      id: 1,
      displayName: "john",
      fullName: "John Doe",
      email: "john@test.com",
      studyLocation: "Madrid",
      roles: ["USER", "ADMIN"]
    };

    const fakeUserDTO: UserDTO = {
        id: 1,
        displayName: "test",
        fullName: "userTest",
        email: "test@email.com",
        studyLocation: "To be filled",
        roles: ["USER"],
        experiences:[]
    }

    const mockGetUser = vi.fn().mockResolvedValue(fakeUser);

    const mockLogOut = vi.fn();

    const setUser = vi.fn();

    const mockDeleteUserById = vi.fn().mockResolvedValue(fakeUserDTO);


    const mockService: UserService = {
      signUp: vi.fn(),
      getUserInfo: vi.fn(),
      getUserById: mockGetUser,
      deleteUserById: mockDeleteUserById,
      getExperiences: vi.fn().mockResolvedValue([]),
      getComments: vi.fn().mockResolvedValue([]),
    };

    const mockAuth: AuthService = {
      logIn: vi.fn(),
      logOut: mockLogOut
    };

    (useUserStore as any).mockReturnValue({
      user: { id: 1 },
      setUser
    });

    render(<UserPage authService={mockAuth} userService={mockService}/>);

    await waitFor(() => {
      expect(mockGetUser).toHaveBeenCalled();
    });

    fireEvent.click(screen.getByRole("button", { name: /delete profile/i }));

    expect(confirmSpy).toHaveBeenCalled();
    expect(mockDeleteUserById).not.toHaveBeenCalled();
  });

});