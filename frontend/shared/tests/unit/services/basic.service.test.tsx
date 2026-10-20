
import { beforeEach, describe, expect, it, vi } from "vitest";

import { createBasicService } from "../../../src/services/basicService";
import type { ApiClient } from "../../../src/api/apiClient";
import { ApiError } from "../../../src/api/apiError";

describe("BasicService", () => {
  let api: {
    get: ReturnType<typeof vi.fn>;
    post: ReturnType<typeof vi.fn>;
    put: ReturnType<typeof vi.fn>;
    delete: ReturnType<typeof vi.fn>;
  };

  let basicService: ReturnType<typeof createBasicService>;

  beforeEach(() => {
    vi.clearAllMocks();

    api = {
      get: vi.fn(),
      post: vi.fn(),
      put: vi.fn(),
      delete: vi.fn(),
    };

    basicService = createBasicService(api as unknown as ApiClient);
  });

  function createResponse(
    body: unknown,
    status = 200,
    ok = true
  ): Response {
    return {
      ok,
      status,
      json: vi.fn().mockResolvedValue(body),
      text: vi.fn().mockResolvedValue(
        typeof body === "string" ? body : JSON.stringify(body)
      ),
    } as unknown as Response;
  }

  describe("getMethod", () => {
    it("should return the parsed response body", async () => {
      const expected = { id: 1, name: "Test" };
      api.get.mockResolvedValue(createResponse(expected));

      const result = await basicService.getMethod<typeof expected>("/test");

      expect(api.get).toHaveBeenCalledExactlyOnceWith("/test");
      expect(result).toEqual(expected);
    });
  });

  describe("postMethod", () => {
    it("should send the body and return the parsed response", async () => {
      const body = { name: "Test" };
      const expected = { id: 1, name: "Test" };

      api.post.mockResolvedValue(createResponse(expected));

      const result = await basicService.postMethod<
        typeof expected,
        typeof body
      >("/test", body);

      expect(api.post).toHaveBeenCalledExactlyOnceWith("/test", body);
      expect(result).toEqual(expected);
    });
  });

  describe("putMethod", () => {
    it("should send the body and return the parsed response", async () => {
      const body = { name: "Updated" };
      const expected = { id: 1, name: "Updated" };

      api.put.mockResolvedValue(createResponse(expected));

      const result = await basicService.putMethod<
        typeof expected,
        typeof body
      >("/test/1", body);

      expect(api.put).toHaveBeenCalledExactlyOnceWith("/test/1", body);
      expect(result).toEqual(expected);
    });
  });

  describe("deleteMethod", () => {
    it("should return the parsed response body", async () => {
      const expected = { deleted: true };
      api.delete.mockResolvedValue(createResponse(expected));

      const result = await basicService.deleteMethod<typeof expected>(
        "/test/1"
      );

      expect(api.delete).toHaveBeenCalledExactlyOnceWith("/test/1");
      expect(result).toEqual(expected);
    });
  });

  describe("handleResponse", () => {
    it("should return undefined when the response status is 204", async () => {
      const response = createResponse(null, 204);
      api.get.mockResolvedValue(response);

      const result = await basicService.getMethod<void>("/test");

      expect(result).toBeUndefined();
      expect(response.json).not.toHaveBeenCalled();
    });

    it("should throw ApiError when the response is not successful", async () => {
      const response = createResponse(
        "Internal server error",
        500,
        false
      );
      api.get.mockResolvedValue(response);

      await expect(
        basicService.getMethod("/test")
      ).rejects.toEqual(
        expect.objectContaining({
          status: 500,
          message: "Internal server error",
        })
      );

      expect(response.text).toHaveBeenCalledOnce();
    });

    it("should preserve the HTTP status and response body in ApiError", async () => {
      const response = createResponse("Not found", 404, false);
      api.delete.mockResolvedValue(response);

      const result = basicService.deleteMethod("/test/999");

      await expect(result).rejects.toBeInstanceOf(ApiError);
      await expect(result).rejects.toMatchObject({
        status: 404,
        message: "Not found",
      });
    });
  });
});
