import { render, screen, waitFor } from "@testing-library/react";
import { describe, it, expect } from "vitest";
import { createExperienceService } from "@shared/services/experience.service";
import "@testing-library/jest-dom";
import { createApiClient } from "@shared/apiClient";
import { APIURL } from "src/config/env";
import ExperiencesPage from "src/pages/ExperiencesPage";

const testAPI = createApiClient(APIURL)
const testService = createExperienceService(testAPI);

describe("Experiences",()=>{
    it("renders data from API",async()=>{

        render(<ExperiencesPage experienceService={testService}/>);

        await waitFor(() => {
            expect(screen.queryAllByRole("heading").length).toBeGreaterThan(0);
            expect(screen.queryAllByText(/^\d{4}-\d{2}-\d{2}$/).length).toBeGreaterThan(0)
        });        
    })
})