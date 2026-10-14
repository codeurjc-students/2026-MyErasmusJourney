import { afterEach, beforeEach, describe, expect, it, vi } from "vitest";
import "@testing-library/jest-dom";
import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { toast } from "sonner";
import { ApiError } from "@shared/api/apiError";
import type { createUserService } from "@shared/services/user.service";
import ProfileImageDialog from "../../../src/components/ProfileImageDialog/ProfileImageDialog";

type UserService = ReturnType<typeof createUserService>;

// navigate is replaced by a mock so redirections can be asserted without a router
const { navigateMock } = vi.hoisted(() => ({
    navigateMock: vi.fn(),
}));

vi.mock("react-router-dom", async (importOriginal) => ({
    ...(await importOriginal<typeof import("react-router-dom")>()),
    useNavigate: () => navigateMock,
}));

vi.mock("sonner", () => ({
    toast: {
        success: vi.fn(),
        error: vi.fn(),
    },
}));

describe("ProfileImageDialog", () => {
    const userId = 7;

    let updateUserImageMock: ReturnType<typeof vi.fn>;
    let setImageUpdatedMock: (newValue: boolean) => void;
    let userService: UserService;

    beforeEach(() => {
        vi.clearAllMocks();

        updateUserImageMock = vi.fn().mockResolvedValue(undefined);
        setImageUpdatedMock = vi.fn<(newValue: boolean) => void>();

        userService = {
            updateUserImage: updateUserImageMock,
        } as unknown as UserService;
    });

    afterEach(() => {
        vi.restoreAllMocks();
    });

    function createImage(name = "profile.png", type = "image/png") {
        return new File(["image content"], name, { type });
    }

    function getFileInput() {
        return document.getElementById("imageFile") as HTMLInputElement;
    }

    async function openDialog() {
        const user = userEvent.setup();

        render(
            <ProfileImageDialog
                userService={userService}
                id={userId}
                setImageUpdated={setImageUpdatedMock}
            />
        );

        await user.click(
            screen.getByRole("button", { name: /change picture/i })
        );

        return user;
    }

    async function selectImageAndUpload(file?: File) {
        const user = await openDialog();

        if (file) {
            await user.upload(getFileInput(), file);
        }

        await user.click(
            screen.getByRole("button", { name: /upload/i })
        );

        return user;
    }

    it("should render the change picture button with the dialog closed", () => {
        render(
            <ProfileImageDialog
                userService={userService}
                id={userId}
                setImageUpdated={setImageUpdatedMock}
            />
        );

        expect(
            screen.getByRole("button", { name: /change picture/i })
        ).toBeInTheDocument();

        expect(screen.queryByRole("dialog")).not.toBeInTheDocument();
    });

    it("should open the dialog and display the file input and the upload button", async () => {
        await openDialog();

        expect(screen.getByRole("dialog")).toBeInTheDocument();

        const fileInput = getFileInput();

        expect(fileInput).toBeInTheDocument();
        expect(fileInput).toHaveAttribute("type", "file");
        expect(fileInput).toHaveAttribute("accept", "image/*");

        expect(
            screen.getByRole("button", { name: /upload/i })
        ).toBeInTheDocument();
    });

    it("should upload the selected image for the given user", async () => {
        const image = createImage();

        await selectImageAndUpload(image);

        await waitFor(() => {
            expect(updateUserImageMock).toHaveBeenCalledExactlyOnceWith(
                userId,
                image
            );
        });
    });

    it("should show a success notification and mark the image as updated after a successful upload", async () => {
        await selectImageAndUpload(createImage());

        await waitFor(() => {
            expect(toast.success).toHaveBeenCalledExactlyOnceWith(
                "Profile Image updated"
            );
        });

        expect(setImageUpdatedMock).toHaveBeenCalledExactlyOnceWith(true);
        expect(toast.error).not.toHaveBeenCalled();
        expect(navigateMock).not.toHaveBeenCalled();
    });

    it("should close the dialog after clicking Upload", async () => {
        await selectImageAndUpload(createImage());

        await waitFor(() => {
            expect(screen.queryByRole("dialog")).not.toBeInTheDocument();
        });
    });

    it("should send an empty file when no image has been selected", async () => {
        await selectImageAndUpload();

        await waitFor(() => {
            expect(updateUserImageMock).toHaveBeenCalledOnce();
        });

        const [id, file] = updateUserImageMock.mock.calls[0];

        expect(id).toBe(userId);
        expect(file).toBeInstanceOf(File);
        expect(file.size).toBe(0);
        expect(file.name).toBe("");
    });

    it("should navigate to the error page when the upload fails with an internal server error", async () => {
        const consoleErrorMock = vi
            .spyOn(console, "error")
            .mockImplementation(() => { });
        const error = new ApiError(500, "Internal server error");

        updateUserImageMock.mockRejectedValue(error);

        await selectImageAndUpload(createImage());

        await waitFor(() => {
            expect(navigateMock).toHaveBeenCalledExactlyOnceWith("/error");
        });

        expect(consoleErrorMock).toHaveBeenCalledExactlyOnceWith(error);
        expect(toast.success).not.toHaveBeenCalled();
        expect(toast.error).not.toHaveBeenCalled();
        expect(setImageUpdatedMock).not.toHaveBeenCalled();
    });

    it("should show an unsupported format notification when the API responds with 415", async () => {
        updateUserImageMock.mockRejectedValue(
            new ApiError(415, "Unsupported Media Type")
        );

        await selectImageAndUpload(createImage("document.gif", "image/gif"));

        await waitFor(() => {
            expect(toast.error).toHaveBeenCalledExactlyOnceWith(
                "Unsupported file format. Please upload images (JPEG, PNG)"
            );
        });

        expect(toast.success).not.toHaveBeenCalled();
        expect(setImageUpdatedMock).not.toHaveBeenCalled();
        expect(navigateMock).not.toHaveBeenCalled();
    });

    it("should show the error message when the upload fails with a generic error", async () => {
        updateUserImageMock.mockRejectedValue(new Error("Network failure"));

        await selectImageAndUpload(createImage());

        await waitFor(() => {
            expect(toast.error).toHaveBeenCalledExactlyOnceWith(
                "Network failure"
            );
        });

        expect(toast.success).not.toHaveBeenCalled();
        expect(setImageUpdatedMock).not.toHaveBeenCalled();
        expect(navigateMock).not.toHaveBeenCalled();
    });

    it("should show a notification and not navigate when the API responds with a client error other than 415", async () => {
        updateUserImageMock.mockRejectedValue(new ApiError(403, "Forbidden"));

        await selectImageAndUpload(createImage());

        await waitFor(() => {
            expect(toast.error).toHaveBeenCalledOnce();
        });

        expect(toast.success).not.toHaveBeenCalled();
        expect(setImageUpdatedMock).not.toHaveBeenCalled();
        expect(navigateMock).not.toHaveBeenCalled();
    });

    it("should show a default notification when the upload fails with a non-Error value", async () => {
        updateUserImageMock.mockRejectedValue("unexpected failure");

        await selectImageAndUpload(createImage());

        await waitFor(() => {
            expect(toast.error).toHaveBeenCalledExactlyOnceWith(
                "Error while uploading the image."
            );
        });

        expect(toast.success).not.toHaveBeenCalled();
        expect(setImageUpdatedMock).not.toHaveBeenCalled();
    });
});
