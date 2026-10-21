import { beforeEach, describe, expect, it, vi } from "vitest";
import { createExperienceService, ExperienceService } from "../../../src/services/experience.service";
import { ApiClient } from "../../../src/api/apiClient";
import { ExperienceFilters } from "../../../src/models/ExperienceFilters";
import { ExperienceFormDTO } from "../../../src/models/ExperienceFormDTO";
import { CommentFormDTO } from "../../../src/models/CommentFormDTO";

const { basicServiceMock } = vi.hoisted(() => ({
  basicServiceMock: {
    getMethod: vi.fn(),
    postMethod: vi.fn(),
    putMethod: vi.fn(),
    deleteMethod: vi.fn(),
  },
}));

vi.mock("../../../src/services/basicService", () => ({
  createBasicService: vi.fn(() => basicServiceMock),
}));

describe("ExperienceService", () => {
  let experienceService: ExperienceService;

  beforeEach(() => {
    vi.clearAllMocks();

    basicServiceMock.getMethod.mockResolvedValue([]);
    basicServiceMock.postMethod.mockResolvedValue([]);

    experienceService = createExperienceService(
      {} as ApiClient
    );
  });

  describe("getAll", () => {
    it("should use default pagination when no filters are provided", async () => {
      await experienceService.getAll({} as ExperienceFilters);

      expect(basicServiceMock.getMethod).toHaveBeenCalledExactlyOnceWith(
        "/experiences/?page=0&size=6"
      );
    });

    it("should include all supplied filters in the URL", async () => {
      const filters: ExperienceFilters = {
        page: 2,
        size: 12,
        minRating: 6,
        maxRating: 9,
        from: "2026-01-01",
        to: "2026-06-30",
        cityName: "Madrid",
        categories: ["CULTURE", "GASTRONOMY"],
      };

      await experienceService.getAll(filters);

      expect(basicServiceMock.getMethod).toHaveBeenCalledExactlyOnceWith(
        "/experiences/?page=2&size=12&minimumRate=6&maximumRate=9&from=2026-01-01&to=2026-06-30&cityName=Madrid&categories=CULTURE&categories=GASTRONOMY"
      );
    });

    it("should preserve pagination when other filters are omitted", async () => {
      await experienceService.getAll({
        page: 1,
        size: 3,
      } as ExperienceFilters);

      expect(basicServiceMock.getMethod).toHaveBeenCalledExactlyOnceWith(
        "/experiences/?page=1&size=3"
      );
    });

    it("should include zero as a valid minimum rating", async () => {
      await experienceService.getAll({
        minRating: 0,
      } as ExperienceFilters);

      expect(basicServiceMock.getMethod).toHaveBeenCalledExactlyOnceWith(
        "/experiences/?page=0&size=6&minimumRate=0"
      );
    });
  });

  describe("addMultimedia", () => {
    it("should send all files in FormData to the experience multimedia endpoint", async () => {
      const firstFile = new File(["image content"], "photo.png", {
        type: "image/png",
      });

      const secondFile = new File(["video content"], "video.mp4", {
        type: "video/mp4",
      });

      await experienceService.addMultimedia(
        [firstFile, secondFile],
        42
      );

      expect(basicServiceMock.postMethod).toHaveBeenCalledExactlyOnceWith(
        "/experiences/42/multimedia",
        expect.any(FormData)
      );

      const formData = basicServiceMock.postMethod.mock.calls[0][1] as FormData;

      expect(formData.getAll("multipartFileList")).toHaveLength(2);
      expect(formData.getAll("multipartFileList")).toEqual([
        firstFile,
        secondFile,
      ]);
    });

    it("should send an empty FormData when no files are provided", async () => {
      await experienceService.addMultimedia([], 42);

      expect(basicServiceMock.postMethod).toHaveBeenCalledExactlyOnceWith(
        "/experiences/42/multimedia",
        expect.any(FormData)
      );

      const formData = basicServiceMock.postMethod.mock.calls[0][1] as FormData;

      expect(formData.getAll("multipartFileList")).toHaveLength(0);
    });
  });

  describe("delegated methods", () => {
      it("should call the correct endpoint for each method", async () => {
        const experienceForm: ExperienceFormDTO = {title: "test", description: "description", categories: ["studies"], date: "2025-11-05", rating:1.4, cityId:1}
        const commentForm: CommentFormDTO = {description: "test"}

        await experienceService.getCategories();
        expect(basicServiceMock.getMethod).toHaveBeenCalledWith(
          "/experiences/categories"
        );
  
        await experienceService.postExperience(experienceForm);
        expect(basicServiceMock.postMethod).toHaveBeenCalledWith("/experiences/", experienceForm);
  
        await experienceService.getExperienceById(7);
        expect(basicServiceMock.getMethod).toHaveBeenCalledWith("/experiences/7");
  
        await experienceService.postComment(1, commentForm);
        expect(basicServiceMock.postMethod).toHaveBeenCalledWith("/experiences/1/comments", commentForm);
  
        await experienceService.getCommentsByExperienceId(1);
        expect(basicServiceMock.getMethod).toHaveBeenCalledWith("/experiences/1/comments");
  
        await experienceService.deleteExperience(10);
        expect(basicServiceMock.deleteMethod).toHaveBeenCalledWith("/experiences/10");
      });
    });
});