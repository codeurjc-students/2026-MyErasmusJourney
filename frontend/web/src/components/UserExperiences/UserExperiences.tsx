import type { userServiceProps } from "@shared/interfaces/userServiceProps";
import type { ExperienceSimpleDTO } from "@shared/models/ExperienceSimpleDTO";
import { createUserService } from "@shared/services/user.service";
import { useUserStore } from "@shared/stores/userStore";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { API } from "../../api/client";
import { createExperienceService } from "@shared/services/experience.service";
import type { experienceServiceProps } from "@shared/interfaces/experienceServiceProps";
import { ApiError } from "@shared/api/apiError";

interface experiencesProps {
    userExperiences: ExperienceSimpleDTO[] | undefined;
}
interface userIdProps {
    userId: number | undefined;
}

export default function UserExperiences({ userService = createUserService(API), experienceService = createExperienceService(API), userExperiences, userId }: userServiceProps & experienceServiceProps & experiencesProps & userIdProps) {

    const { user } = useUserStore();

    const [loading, setLoading] = useState<boolean>(true);

    const [experiences, setExperiences] = useState<ExperienceSimpleDTO[]>([]);

    const navigate = useNavigate();

    let id = 0;

    const fetchExperiences = async () => {
        if (userId !== undefined) {
            id = userId;
        }
        else if (user !== null) {
            id = user.id;
        }
        try {
            const data = await userService.getExperiences(id);
            setExperiences(data.reverse());
        } catch (error) {
            if (error instanceof ApiError && error.status >= 500) {
                console.error(error);
                navigate("/error");
                return;
            }
        }
    };

    useEffect(() => {

        if (userExperiences === undefined || userExperiences.length < 1) {
            fetchExperiences();
        }
        else {
            setExperiences(userExperiences)
        }
        setLoading(false);
    }, [])

    async function handleDeleteExperience(experienceId: number) {

        try {
            await experienceService.deleteExperience(experienceId)
            await fetchExperiences();
        }
        catch (error) {
            alert("Error deleting experience:" + error)
        }
    }

    return (<>
        <div className="md:pr-8 md:border-r">
            <h4 className="mb-6">Experiences</h4>
            {loading
                ? (
                    <p>Loading experiences...</p>
                ) : (
                    <div className="flex flex-col gap-4">
                        {experiences.map((experience) => (
                            <div id={`experience-${experience.id}`} className="w-full rounded-2xl bg-white shadow-md p-4 flex items-center justify-between gap-4 transition hover:shadow-lg">
                                <div className="min-w-0 flex-1">
                                    <p className="truncate text-lg sm:text-xl md:text-2xl font-medium">{experience.title}</p>
                                </div>

                                <div className="flex shrink-0 items-center gap-2">
                                    <Link to={`/experiences/${experience.id}`} aria-label={`View ${experience.title}`} className="flex items-center justify-center w-10 h-10 sm:w-11 sm:h-11 rounded-full bg-[#4A90D9] text-white hover:opacity-80 transition">
                                        👁
                                    </Link>

                                    <button type="button" aria-label={`Delete ${experience.title}`} onClick={() => (handleDeleteExperience(experience.id))} className="flex items-center justify-center w-10 h-10 sm:w-11 sm:h-11 rounded-full bg-[#4A90D9] text-white hover:opacity-80 transition">
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