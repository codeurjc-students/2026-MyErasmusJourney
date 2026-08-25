import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import { describe, it, expect } from "vitest";
import "@testing-library/jest-dom";
import Experience from "../../../src/components/Experience";


describe("Experience Component", () => {
  it("renders component", async () => {
    const experience = { id: 1, title: "Title 1", date:"2026-06-25", rating:7.32, description:"Description 1"}
    
    render(
      <MemoryRouter>
        <Experience experience={experience} />
      </MemoryRouter>
    );

    await waitFor(()=>{
        expect(screen.getByText("Title 1")).toBeInTheDocument();
        expect(screen.getByText("Description 1")).toBeInTheDocument();
        expect(screen.getByText("7.3")).toBeInTheDocument();
        expect(screen.getByText("2026-06-25")).toBeInTheDocument();
    })
  });

  it("renders experience with empty fields", async () => {
    const experience = { id: 1, title: "Title 1", date: "", rating:7.32, description:"Description 1"} //missing date

    render(
      <MemoryRouter>
        <Experience experience={experience} />
      </MemoryRouter>
    );

    await waitFor(()=>{
        expect(screen.getByText("7.3")).toBeInTheDocument();
        expect(screen.queryAllByText(/^\d{4}-\d{2}-\d{2}$/)).toHaveLength(0);
    })
  });

  it("renders experience with an empty description", async () => {
    const experience = { id: 1, title: "Title 1", date: "2026-06-25", rating:7.32, description:""} //missing date

    render(
      <MemoryRouter>
        <Experience experience={experience} />
      </MemoryRouter>
    );

    await waitFor(()=>{
        expect(screen.queryAllByText("Description 1")).toHaveLength(0);
        expect(screen.getByText("Title 1")).toBeInTheDocument();
    })
  });

  it("renders experience with a rating of 0", async () => {
    const experience = { id: 1, title: "Title 1", date:"2026-06-25", rating:0, description:"Description 1"}
    
    render(
      <MemoryRouter>
        <Experience experience={experience} />
      </MemoryRouter>
    );

    await waitFor(()=>{
        expect(screen.getByText("Title 1")).toBeInTheDocument();
        expect(screen.getByText("Description 1")).toBeInTheDocument();
        expect(screen.getByText("0.0")).toBeInTheDocument();
        expect(screen.getByText("2026-06-25")).toBeInTheDocument();
    })
  });

});