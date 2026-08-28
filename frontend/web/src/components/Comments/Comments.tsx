import { createExperienceService } from "@shared/services/experience.service";
import { API } from "../../api/client";
import { useEffect, useState, type ChangeEvent } from "react";

import type { experienceServiceProps } from "@shared/interfaces/experienceServiceProps";
import type { CommentFormDTO } from "@shared/models/CommentFormDTO";
import type { CommentSimpleDTO } from "@shared/models/CommentSimpleDTO";
import { useUserStore } from "@shared/stores/userStore";
import { Link } from "react-router-dom";

interface ExperienceIdProps{
    experienceId: number;
}


export default function Comments({ experienceService = createExperienceService(API), experienceId }: experienceServiceProps & ExperienceIdProps){

    const{user} = useUserStore();

    const [description, setDescription] = useState("");

    const [comments, setComments] = useState<CommentSimpleDTO[]>([]);

    function onChange(e: ChangeEvent<HTMLInputElement>) {
        setDescription(e.target.value);
    }

    async function getComments(){
        const data = await experienceService.getCommentsByExperienceId(experienceId);
        setComments(data);
    }

    async function postComment(){
        const newComment: CommentFormDTO = {description: description};
        await experienceService.postComment(experienceId, newComment);
        await getComments();
        setDescription("");
    }

    useEffect(()=>{
        const fetchData = async () => {
            await getComments()
        }
        fetchData();
    },[])


    return(<>
        <div className="flex flex-col gap-4 flex-1">

            <div className="space-y-4">
                {comments.map((comment)=>(
                    <div className="rounded-2xl bg-white shadow-md p-4">
                        <div className="flex items-center justify-between gap-3">
                            <div className="flex items-center gap-3">
                                <img src="/images/available_soon.png" alt="Comment author profile" className="w-10 h-10 rounded-full object-cover" />
                                <p className="font-bold">{comment.authorName}</p>
                            </div>
                            <p className="text-sm opacity-70">{comment.date}</p>
                        </div>
                        <p className="mt-3">{comment.description}</p>
                    </div>
                ))}
            </div>

        </div>
            {user !== null
                ?(
                    <div className="flex items-center gap-3 mt-6">
                        <input type="text" placeholder="Share your opinion..." className="flex-1 min-w-0" name="description" value={description} onChange={onChange}/>
                        <button type="button" aria-label="Send comment" className="shrink-0 w-11 h-11 flex items-center justify-center rounded-full p-2" onClick={postComment}>
                            ➤
                        </button>
                    </div>
                ):(
                    <div className="flex items-center gap-3 mt-6">
                        <p>Enjoyed this experience? <Link to={"/log-in"} className="link"> Sign in </Link>and share your thoughts! </p>
                    </div>
                )
            }
    </>)
}