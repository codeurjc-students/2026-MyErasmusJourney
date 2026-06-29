import { render, screen, waitFor } from "@testing-library/react";
import { describe, it, expect, vi } from "vitest";
import ExperiencesPage from "../../../src/pages/ExperiencesPage";
import { createExperienceService } from "@shared/services/experience.service";
import "@testing-library/jest-dom";

// 1. MOCK of service
vi.mock("@shared/services/experience.service", () => {
  return {
    createExperienceService: vi.fn(),
  };
});

describe("ExperiencesPage", () => {
  it("renders all items of experience list", async () => {

    // 2. mocked data
    const fakeData = [
      { id: 1, title: "Title 1", date:"2026-06-25", rating:7.32, description:"description 1"},
      { id: 2, title: "Title 2", date:"2026-06-25", rating: 2.95, description: "description 2"},
    ];

    // 3. mock of getAll
    const mockGetAll = vi.fn().mockResolvedValue(fakeData);

    // 4. return mocked service
    (createExperienceService as any).mockReturnValue({
      getAll: mockGetAll,
    });

    // 5. render component (DOM virtual)
    render(<ExperiencesPage />);

    // 6. assertions
    await waitFor(() => {
      expect(screen.getByText("Title 1")).toBeInTheDocument();
      expect(screen.getByText("Title 2")).toBeInTheDocument();
    });

    // 7. verfies the service was called
    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });

  it("renders empty where data is empty", async () => {

    const mockGetAll = vi.fn().mockResolvedValue([]);

    (createExperienceService as any).mockReturnValue({
      getAll: mockGetAll,
    });

    render(<ExperiencesPage />);

    await waitFor(() => {
      expect(screen.queryAllByText("Title")).toHaveLength(0);
    });

    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });

  it("renders data in the right order", async () => {

    const fakeData = [
      { id: 1, title: "Title 1", date:"2026-06-25", rating:7.32, description:"description 1"},
      { id: 2, title: "Title 2", date:"2026-06-25", rating: 2.95, description: "description 2"},
      { id: 3, title: "Title 3", date:"2026-06-25", rating: 4.81, description: "description 3"},
    ];

    const mockGetAll = vi.fn().mockResolvedValue(fakeData);

    (createExperienceService as any).mockReturnValue({
      getAll: mockGetAll,
    });

    render(<ExperiencesPage />);

    await waitFor(() => {
      const experiences = screen.queryAllByText(/^Title /i);

      expect(experiences).toHaveLength(3);

      expect(experiences[0]).toHaveTextContent("Title 1");
      expect(experiences[1]).toHaveTextContent("Title 2");
      expect(experiences[2]).toHaveTextContent("Title 3");
    }); 

    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });

  it("renders a single experience", async () => {
    const fakeData = [
      { id: 1, title: "Title 1", date:"2026-06-25", rating:7.32, description:"description 1"}
    ];

    const mockGetAll = vi.fn().mockResolvedValue(fakeData);

    (createExperienceService as any).mockReturnValue({
      getAll: mockGetAll,
    });

    render(<ExperiencesPage />);

    await waitFor(() => {
      const experiences = screen.queryAllByText(/^Title /i);

      expect(experiences).toHaveLength(1);

      expect(experiences[0]).toHaveTextContent("Title 1");
    }); 

    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });

  it("renders duplicated experiences", async () => {
    const fakeData = [
      { id: 1, title: "Title 1", date:"2026-06-25", rating:7.32, description:"description 1"},
      { id: 1, title: "Title 1", date:"2026-06-25", rating:7.32, description:"description 1"}
    ];

    const mockGetAll = vi.fn().mockResolvedValue(fakeData);

    (createExperienceService as any).mockReturnValue({
      getAll: mockGetAll,
    });

    render(<ExperiencesPage />);

    await waitFor(() => {

      expect(screen.getAllByText("Title 1")).toHaveLength(2);
      expect(screen.getAllByText("description 1")).toHaveLength(2);
      expect(screen.getAllByText("2026-06-25")).toHaveLength(2);
      expect(screen.getAllByText(7.32)).toHaveLength(2);
    }); 

    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });

  it("handles service errors", async () => {


    const mockGetAll = vi.fn().mockRejectedValue(new Error("Mocked error"));

    const consoleErrorSpy = vi.spyOn(console, "error").mockImplementation(() => {});

    (createExperienceService as any).mockReturnValue({
      getAll: mockGetAll,
    });

    render(<ExperiencesPage />);

    await waitFor(() => {
      const experiences = screen.queryAllByText(/^Title /i);

      expect(experiences).toHaveLength(0);
      expect(consoleErrorSpy).toHaveBeenCalled();
    }); 

    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });

  it("renders experiences when service returns many items", async () => {
    const fakeData = [
      { id: 1, title: "Title 1",  date: "2026-06-01", rating: 8.4, description: "Description 1" },
      { id: 2, title: "Title 2",  date: "2026-06-02", rating: 7.1, description: "Description 2" },
      { id: 3, title: "Title 3",  date: "2026-06-03", rating: 9.8, description: "Description 3" },
      { id: 4, title: "Title 4",  date: "2026-06-04", rating: 6.5, description: "Description 4" },
      { id: 5, title: "Title 5",  date: "2026-06-05", rating: 5.3, description: "Description 5" },
      { id: 6, title: "Title 6",  date: "2026-06-06", rating: 4.7, description: "Description 6" },
      { id: 7, title: "Title 7",  date: "2026-06-07", rating: 8.9, description: "Description 7" },
      { id: 8, title: "Title 8",  date: "2026-06-08", rating: 3.2, description: "Description 8" },
      { id: 9, title: "Title 9",  date: "2026-06-09", rating: 9.1, description: "Description 9" },
      { id: 10, title: "Title 10", date: "2026-06-10", rating: 7.7, description: "Description 10" },
    ];

    const mockGetAll = vi.fn().mockResolvedValue(fakeData);

    (createExperienceService as any).mockReturnValue({
      getAll: mockGetAll,
    });

    render(<ExperiencesPage />);

    await waitFor(() => {
      const experiences = screen.queryAllByText(/^Title /i);

      expect(experiences).toHaveLength(10);
      expect(screen.getByText("Title 5")).toBeInTheDocument();
      expect(screen.getByText("Title 10")).toBeInTheDocument();
    }); 

    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });
});