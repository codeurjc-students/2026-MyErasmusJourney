import type { userServiceProps } from "@shared/interfaces/userServiceProps";
import type { CommentSimpleDTO } from "@shared/models/CommentSimpleDTO";
import { createUserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { API } from "../../api/client";
import { ApiError } from "@shared/api/apiError";

interface CommentsProps {
    userComments: CommentSimpleDTO[] | undefined;
}
interface userIdProps {
    userId: number | undefined;
}

export default function UserComments({ userService = createUserService(API), userComments, userId }: userServiceProps & CommentsProps & userIdProps) {

    const { user } = useUserStore();

    const [loading, setLoading] = useState<boolean>(true);

    const [comments, setComments] = useState<CommentSimpleDTO[]>([]);

    const navigate = useNavigate();

    let id = 0;

    useEffect(() => {
        const fetchExperiences = async () => {
            if (userId !== undefined) {
                id = userId;
            }
            else if (user !== null) {
                id = user.id;
            }
            try {

            } catch (error) {
                if (error instanceof ApiError && error.status >= 500) {
                    console.error(error);
                    navigate("/error");
                    return;
                }
            }
            const data = await userService.getComments(id);
            setComments(data.reverse());
        };
        console.log(userComments)

        if (userComments === undefined || userComments.length < 1) {
            fetchExperiences();
        }
        else {
            setComments(userComments)
        }
        setLoading(false);
    }, [])

    return (<>
        <div className="md:pl-8">
            <h4 className="mb-6">Comments</h4>
            {loading
                ? (
                    <p>Loading comments...</p>
                ) : (
                    <div className="flex flex-col gap-4">
                        {comments.map((comment) => (
                            <div id={`experience-${comment.id}`} className="w-full rounded-2xl bg-white shadow-md p-4 flex items-center justify-between gap-4 transition hover:shadow-lg">
                                <div className="min-w-0 flex-1">
                                    <p className="truncate text-lg sm:text-xl md:text-2xl font-medium">{comment.description}</p>
                                </div>

                                <div className="flex shrink-0 items-center gap-2">
                                    <Link to={`/experiences/${comment.experienceId}`} aria-label={`View ${comment.description}`} className="flex items-center justify-center w-10 h-10 sm:w-11 sm:h-11 rounded-full bg-[#4A90D9] text-white hover:opacity-80 transition">
                                        👁
                                    </Link>

                                    <button type="button" aria-label={`Delete ${comment.description}`} className="flex items-center justify-center w-10 h-10 sm:w-11 sm:h-11 rounded-full bg-[#4A90D9] text-white hover:opacity-80 transition">
                                        🗑
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                )}

        </div>
    </>)
}