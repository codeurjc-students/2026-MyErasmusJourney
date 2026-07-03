import { describe, expect, it, vi } from "vitest";
import { createExperienceService } from "../../../src/services/experience.service";

describe("ExperienceService", () => {
  it("should return all experiences when the request succeeds", async () => {

    const experiences = [
      { id: 1, title: "Experience 1" },
      { id: 2, title: "Experience 2" },
    ];

    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue(experiences),
      }),
    };

    const service = createExperienceService(mockApi);

    const result = await service.getAll();

    expect(mockApi.get).toHaveBeenCalledTimes(1);
    expect(mockApi.get).toHaveBeenCalledWith("/experiences/");
    expect(result).toEqual(experiences);
  });

  it("should throw an error when the request fails", async () => {
    
    const mockApi = {
      get: vi.fn().mockResolvedValue({
        ok: false,
      }),
    };

    const service = createExperienceService(mockApi);

    await expect(service.getAll()).rejects.toThrow(
      "Error fetching experiences"
    );

    expect(mockApi.get).toHaveBeenCalledWith("/experiences/");
  });
});