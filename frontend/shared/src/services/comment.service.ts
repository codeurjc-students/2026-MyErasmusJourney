import type { ApiClient } from "../api/apiClient";
import type {CommentDTO} from "../models/CommentDTO";
import { ApiError } from "../api/apiError";
import { createBasicService } from "./basicService";

export type CommentService = ReturnType<typeof createCommentService>;

export function createCommentService(api: ApiClient) {
    const basicService = createBasicService(api)
    return {
        deleteComment: (id: number) => basicService.deleteMethod<CommentDTO>(`/comments/${id}`),
    };
}

async function deleteComment(api: ApiClient, id: number) {
    const response = await api.delete(`/comments/${id}`)

    if (!response.ok) {
        throw new ApiError(response.status, await response.text());
    }

    return response.json();

}