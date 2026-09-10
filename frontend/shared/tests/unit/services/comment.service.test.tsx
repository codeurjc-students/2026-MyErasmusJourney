import { describe, expect, it, vi } from "vitest";
import { ApiClient } from "../../../src/api/apiClient";
import {createCommentService} from "../../../src/services/comment.service";
import type {CommentDTO} from "../../../src/models/CommentDTO";


describe("CommentService", () => {

    it("should return the deleted comment when success", async () => {

        const responseData: CommentDTO = {
            id: 1,
            date: "2026-06-25",
            description: "Comment that will be deleted",
            author: {
                id: 1,
                displayName: "Jeremy",
                email: ""
            },
            experience: {
                id: 1,
                title: "Experience",
                date: "2026-06-25",
                cityName: "Munich",
                country: "Germany",
                categories: ["Studies"],
                rating: 8.5,
                description: "Experience",
                authorName: "Jeremy",
            }
        }

        const fakeResponse = {
            ok: true,
            status: 200,
            json: vi.fn().mockResolvedValue(responseData),
        };

        const mockDelete = vi.fn().mockResolvedValue(fakeResponse);

        const mockAPI: ApiClient = {
            delete: mockDelete,
        };

        const commentService = createCommentService(mockAPI);

        const result = await commentService.deleteComment(responseData.id);

        expect(result).toEqual(responseData);
        expect(fakeResponse.json).toHaveBeenCalledTimes(1);
    });

    it("should throw error when posting experience fails", async () => {

        const fakeResponse = {
            ok: false,
            json: vi.fn(),
            status: 403,
            text: vi.fn().mockReturnValue("Forbidden"),
        };

        const mockDelete = vi.fn().mockResolvedValue(fakeResponse);

        const mockAPI: ApiClient = {
            get: vi.fn(),
            delete: mockDelete,
        };

        const commentService = createCommentService(mockAPI);

        await expect(commentService.deleteComment(1)).rejects.toThrow(
            "Forbidden"
        );
    });
})
