import type { ApiClient } from "../api/apiClient";
import { ApiError } from "../api/apiError";

export type CommentService = ReturnType<typeof createCommentService>;

export function createCommentService(api: ApiClient) {
    return {
        deleteComment: (id: number) => deleteComment(api, id),
    };
}

async function deleteComment(api: ApiClient, id: number) {
    const response = await api.delete(`/comments/${id}`)

    if (!response.ok) {
        throw new ApiError(response.status, await response.text());
    }

    return response.json();

}