import { render, screen, fireEvent } from "@testing-library/react";
import { describe, it, expect, vi } from "vitest";
import "@testing-library/jest-dom";
import { useNavigate } from "react-router-dom";
import AvailableSoonPage from "src/pages/AvailableSoonPage/AvailableSoonPage";

vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual("react-router-dom");

  return {
    ...actual,
    useNavigate: vi.fn(),
  };
});

describe("Avaliable Soon Page", () => {
  it("should navigate to home when clicking the button", () => {
    const mockNavigate = vi.fn();

    vi.mocked(useNavigate).mockReturnValue(mockNavigate);

    render(<AvailableSoonPage />);

    const button = screen.getByRole("button", {
      name: /back to home page/i,
    });

    fireEvent.click(button);

    expect(mockNavigate).toHaveBeenCalledTimes(1);
    expect(mockNavigate).toHaveBeenCalledWith("/");
  });
});