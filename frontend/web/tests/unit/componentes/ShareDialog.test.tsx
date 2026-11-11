import { afterEach, beforeEach, describe, expect, it, vi, } from "vitest";
import "@testing-library/jest-dom";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { toast } from "sonner";
import ShareDialog from "../../../src/components/ShareDialog/ShareDialog";


vi.mock("sonner", () => ({
    toast: {
        info: vi.fn(),
    },
}));

describe("ShareDialog", () => {
    const experienceUrl = "http://localhost:3000/experiences/42";

    let writeTextMock: ReturnType<typeof vi.fn>;
    let windowOpenMock: ReturnType<typeof vi.spyOn>;

    beforeEach(() => {
        vi.clearAllMocks();

        window.history.pushState({}, "", "/experiences/42");

        writeTextMock = vi.fn().mockResolvedValue(undefined);
        windowOpenMock = vi.fn<typeof window.open>();

        vi.spyOn(window, "open").mockImplementation(windowOpenMock);

        Object.defineProperty(navigator, "clipboard", {
            configurable: true,
            value: {
                writeText: writeTextMock,
            },
        });


        Object.defineProperty(navigator, "share", {
            configurable: true,
            writable: true,
            value: undefined,
        });
    });

    afterEach(() => {
        vi.restoreAllMocks();
    });

    async function openDialog() {
        const user = userEvent.setup();

        render(<ShareDialog />);

        await user.click(
            screen.getByRole("button", { name: /share experience/i })
        );

        return user;
    }

    it("should render the share button", () => {
        render(<ShareDialog />);

        expect(
            screen.getByRole("button", { name: /share experience/i })
        ).toBeInTheDocument();
    });

    it("should open the dialog and display its content", async () => {
        await openDialog();

        expect(
            screen.getByRole("dialog")
        ).toBeInTheDocument();

        expect(
            screen.getByRole("heading", { name: /share experience/i })
        ).toBeInTheDocument();

        expect(
            screen.getByText(
                /share this experience with your friends and inspire other erasmus students/i
            )
        ).toBeInTheDocument();

        expect(
            screen.getByRole("button", { name: /copy link/i })
        ).toBeInTheDocument();

        expect(
            screen.getByRole("button", { name: /whatsapp/i })
        ).toBeInTheDocument();

        expect(
            screen.getByRole("button", { name: /x\/twitter/i })
        ).toBeInTheDocument();

        expect(
            screen.getByRole("button", { name: /^other$/i })
        ).toBeInTheDocument();
    });

    it("should display the current experience URL in the input", async () => {
        await openDialog();

        const linkInput = screen.getByRole("textbox", {
            name: /experience link/i,
        });

        expect(linkInput).toHaveValue(experienceUrl);
        expect(linkInput).toHaveAttribute("readonly");
    });

    it("should copy the experience URL to the clipboard", async () => {

        render(<ShareDialog />);

        fireEvent.click(
            screen.getByRole("button", { name: /share experience/i })
        );

        fireEvent.click(
            screen.getByRole("button", { name: /copy link/i })
        );

        await waitFor(() => {
            expect(writeTextMock).toHaveBeenCalledWith(
                window.location.href
            );
        });
    });

    it("should open WhatsApp with the encoded sharing message", async () => {
        const user = await openDialog();

        await user.click(
            screen.getByRole("button", { name: /whatsapp/i })
        );

        const message =
            `Check out this experience on MyErasmusJourney!\n` +
            `Discover all the details here:\n` +
            `${experienceUrl}\n` +
            `Ready to experience it yourself?`;

        expect(windowOpenMock).toHaveBeenCalledExactlyOnceWith(
            `https://wa.me/?text=${encodeURIComponent(message)}`,
            "_blank",
            "noopener,noreferrer"
        );
    });

    it("should open X/Twitter with the encoded sharing message", async () => {
        const user = await openDialog();

        await user.click(
            screen.getByRole("button", { name: /x\/twitter/i })
        );

        const message =
            `Check out this experience on MyErasmusJourney!\n` +
            `Discover all the details here:\n` +
            `${experienceUrl}\n` +
            `Ready to experience it yourself?`;

        expect(windowOpenMock).toHaveBeenCalledExactlyOnceWith(
            `https://twitter.com/intent/tweet?url=${encodeURIComponent(message)}`,
            "_blank",
            "noopener,noreferrer"
        );
    });

    it("should share the experience using the Web Share API when available", async () => {
        const shareMock = vi.fn().mockResolvedValue(undefined);

        Object.defineProperty(navigator, "share", {
            configurable: true,
            writable: true,
            value: shareMock,
        });

        const user = await openDialog();

        await user.click(
            screen.getByRole("button", { name: /^other$/i })
        );

        expect(shareMock).toHaveBeenCalledExactlyOnceWith({
            title: "MyErasmusJourney",
            text: "Check out this experience on MyErasmusJourney!",
            url: experienceUrl,
        });

        expect(writeTextMock).not.toHaveBeenCalled();
        expect(toast.info).not.toHaveBeenCalled();
    });

    it("should copy the URL and display a notification when Web Share API is unavailable", async () => {
        render(<ShareDialog />);

        fireEvent.click(
            screen.getByRole("button", { name: /share experience/i })
        );

        fireEvent.click(
            await screen.findByRole("button", { name: /^other$/i })
        );

        await waitFor(() => {
            expect(writeTextMock).toHaveBeenCalledWith(
                window.location.href
            );
        });

        expect(toast.info).toHaveBeenCalledWith(
            "Unable to share experience, the link has been copied into the clipboard"
        );
    });

    it("should log an error when the Web Share API rejects", async () => {
        const error = new Error("Sharing cancelled");
        const shareMock = vi.fn().mockRejectedValue(error);
        const consoleErrorMock = vi
            .spyOn(console, "error")
            .mockImplementation(() => { });

        Object.defineProperty(navigator, "share", {
            configurable: true,
            writable: true,
            value: shareMock,
        });

        const user = await openDialog();

        await user.click(
            screen.getByRole("button", { name: /^other$/i })
        );

        await waitFor(() => {
            expect(consoleErrorMock).toHaveBeenCalledExactlyOnceWith(
                error
            );
        });

        expect(writeTextMock).not.toHaveBeenCalled();
    });

    it("should close the dialog when clicking Close", async () => {
        const user = await openDialog();

        expect(screen.getByRole("dialog")).toBeInTheDocument();

        await user.click(
            document.querySelector<HTMLButtonElement>(".share-close-button")!
        );

        await waitFor(() => {
            expect(screen.queryByRole("dialog")).not.toBeInTheDocument();
        });
    });
});
