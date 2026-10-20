import type { ApiClient } from "../api/apiClient";
import type {CommentDTO} from "../models/CommentDTO";
import { createBasicService } from "./basicService";

export type CommentService = ReturnType<typeof createCommentService>;

export function createCommentService(api: ApiClient) {
    const basicService = createBasicService(api)
    return {
        deleteComment: (id: number) => basicService.deleteMethod<CommentDTO>(`/comments/${id}`),
    };
}