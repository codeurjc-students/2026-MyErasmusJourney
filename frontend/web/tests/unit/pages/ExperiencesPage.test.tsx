import { render, screen, waitFor } from "@testing-library/react";
import { describe, it, expect, vi } from "vitest";
import ExperiencesPage from "../../../src/pages/ExperiencesPage/ExperiencesPage";
import { MemoryRouter } from "react-router-dom";
import type { ExperienceService } from "@shared/services/experience.service";
import "@testing-library/jest-dom";
import type { Page } from "@shared/models/Page";
import type { ExperienceSimpleDTO } from "@shared/models/ExperienceSimpleDTO";
import { ApiError } from "@shared/api/apiError";

describe("ExperiencesPage", () => {
  it("renders all items of experience list", async () => {

    //mocked data
    const fakeData : ExperienceSimpleDTO[] = [
      { id: 1, title: "Title 1", date:"2026-06-25", rating:7.32, description:"description 1", authorName: "test", cityName: "", country:"", categories: ["Studies"]},
      { id: 2, title: "Title 2", date:"2026-06-25", rating: 2.95, description: "description 2", authorName: "test", cityName: "", country:"", categories: ["Studies"]},
    ];

    const page : Page = {size: 2, number:0, totalElements: 2, totalPages:0}
    //mock of getAll
    const mockGetAll = vi.fn().mockResolvedValue({ content: fakeData, page: page});
    //return mocked service
    const mockService: ExperienceService = {
      getAll: mockGetAll,
    };

    //render component (DOM virtual)
    render(
      <MemoryRouter>
        <ExperiencesPage experienceService={mockService} />
      </MemoryRouter>
    );

    //assertions
    await waitFor(() => {
      expect(screen.getByText("Title 1")).toBeInTheDocument();
      expect(screen.getByText("Title 2")).toBeInTheDocument();
    });

    //verfies the service was called
    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });

  it("renders empty when data is empty", async () => {

    const mockGetAll = vi.fn().mockResolvedValue({ content: [] });

    const mockService: ExperienceService = {
      getAll: mockGetAll,
    };

    render(
      <MemoryRouter>
        <ExperiencesPage experienceService={mockService} />
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.queryAllByText("Title")).toHaveLength(0);

      //only the Experiences header is render, no experiences to render no more headers
      expect(screen.queryAllByRole("heading")).toHaveLength(1); 
    });

    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });

  it("renders data in the right order", async () => {

    const fakeData : ExperienceSimpleDTO[] = [
      { id: 1, title: "Title 1", date:"2026-06-25", rating:7.32, description:"description 1",  authorName: "test", cityName: "", country:"", categories: ["Studies"]},
      { id: 2, title: "Title 2", date:"2026-06-25", rating: 2.95, description: "description 2",  authorName: "test", cityName: "", country:"", categories: ["Studies"]},
      { id: 3, title: "Title 3", date:"2026-06-25", rating: 4.81, description: "description 3",  authorName: "test", cityName: "", country:"", categories: ["Studies"]},
    ];

    const page : Page = {size: 3, number:0, totalElements: 3, totalPages:0}

    const mockGetAll = vi.fn().mockResolvedValue({ content: fakeData , page: page});

    const mockService: ExperienceService = {
      getAll: mockGetAll,
    };

    render(
      <MemoryRouter>
        <ExperiencesPage experienceService={mockService} />
      </MemoryRouter>
    );

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
    const fakeData: ExperienceSimpleDTO[] = [
      { id: 1, title: "Title 1", date:"2026-06-25", rating:7.32, description:"description 1", authorName: "test", cityName: "", country:"", categories: ["Studies"]}
    ];

    const page : Page = {size: 1, number:0, totalElements: 1, totalPages:0}


    const mockGetAll = vi.fn().mockResolvedValue({ content: fakeData, page: page});

    const mockService: ExperienceService = {
      getAll: mockGetAll,
    };

    render(
      <MemoryRouter>
        <ExperiencesPage experienceService={mockService} />
      </MemoryRouter>
    );

    // wait for the experience title to appear
    const titleEl = await screen.findByText("Title 1");
    expect(titleEl).toBeInTheDocument();

    const experiences = screen.queryAllByText(/^Title /i);
    expect(experiences).toHaveLength(1);

    expect(experiences[0]).toHaveTextContent("Title 1");

    // header + experience title (experience title may be rendered as heading depending on markup)
    expect(screen.queryAllByRole("heading")).toHaveLength(1);
    

    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });

  it("renders duplicated experiences", async () => {
    const fakeData: ExperienceSimpleDTO[] = [
      { id: 1, title: "Title 1", date:"2026-06-25", rating:7.32, description:"description 1", authorName: "test", cityName: "", country:"", categories: ["Studies"]},
      { id: 1, title: "Title 1", date:"2026-06-25", rating:7.32, description:"description 1", authorName: "test", cityName: "", country:"", categories: ["Studies"]}
    ];

    const page : Page = {size: 2, number:0, totalElements: 2, totalPages:0}

    const mockGetAll = vi.fn().mockResolvedValue({ content: fakeData, page: page });

    const mockService: ExperienceService = {
      getAll: mockGetAll,
    };

    render(
      <MemoryRouter>
        <ExperiencesPage experienceService={mockService} />
      </MemoryRouter>
    );


    await waitFor(() => {

      expect(screen.getAllByText("Title 1")).toHaveLength(2);
      expect(screen.getAllByText("description 1")).toHaveLength(2);
      expect(screen.getAllByText("2026-06-25")).toHaveLength(2);
      expect(screen.getAllByText("7.3")).toHaveLength(2);
    }); 

    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });

  it("handles service errors", async () => {
    const error = new ApiError(400,"Error fetching experience");

    const mockGetAll = vi.fn().mockRejectedValue(error);

    const consoleErrorSpy = vi.spyOn(console, "error").mockImplementation(() => {});

    const mockService: ExperienceService = {
      getAll: mockGetAll,
    };

    render(
      <MemoryRouter>
        <ExperiencesPage experienceService={mockService} />
      </MemoryRouter>
    );


    await waitFor(() => {
      const experiences = screen.queryAllByText(/^Title /i);

      expect(experiences).toHaveLength(0);
      expect(consoleErrorSpy).toHaveBeenCalled();
    }); 

    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });

  it("renders experiences when service returns many items", async () => {
    const fakeData: ExperienceSimpleDTO[] = [
      { id: 1, title: "Title 1",  date: "2026-06-01", rating: 8.4, description: "Description 1",authorName: "test", cityName: "", country:"", categories: ["Studies"] },
      { id: 2, title: "Title 2",  date: "2026-06-02", rating: 7.1, description: "Description 2",authorName: "test", cityName: "", country:"", categories: ["Studies"] },
      { id: 3, title: "Title 3",  date: "2026-06-03", rating: 9.8, description: "Description 3",authorName: "test", cityName: "", country:"", categories: ["Studies"] },
      { id: 4, title: "Title 4",  date: "2026-06-04", rating: 6.5, description: "Description 4",authorName: "test", cityName: "", country:"", categories: ["Studies"] },
      { id: 5, title: "Title 5",  date: "2026-06-05", rating: 5.3, description: "Description 5",authorName: "test", cityName: "", country:"", categories: ["Studies"] },
      { id: 6, title: "Title 6",  date: "2026-06-06", rating: 4.7, description: "Description 6",authorName: "test", cityName: "", country:"", categories: ["Studies"] },
    ];

    const page : Page = {size: 6, number:0, totalElements: 10, totalPages:2}

    const mockGetAll = vi.fn().mockResolvedValue({ content: fakeData, page: page });

    const mockService: ExperienceService = {
      getAll: mockGetAll,
    };

    render(
      <MemoryRouter>
        <ExperiencesPage experienceService={mockService} />
      </MemoryRouter>
    );

    await waitFor(() => {
      const experiences = screen.queryAllByText(/^Title /i);

      expect(experiences).toHaveLength(6);
      expect(screen.getByText("Title 1")).toBeInTheDocument();
      expect(screen.getByText("Title 6")).toBeInTheDocument();
    }); 

    expect(mockGetAll).toHaveBeenCalledTimes(1);
  });
});