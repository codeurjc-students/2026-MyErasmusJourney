import type { experienceServiceProps } from "@shared/interfaces/experienceServiceProps";
import type { ExperienceDTO } from "@shared/models/ExperienceDTO";
import { createExperienceService } from "@shared/services/experience.service";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { API } from "../../api/client";
import Comments from "../../components/Comments/Comments";

const categoryStyles: Record<string, string> = {
    STUDIES: "#4A90D9",
    ACCOMMODATION: "#63C7BE",
    DOCUMENTATION: "#7E8FA6",
    PERSONAL_EXPERIENCE: "#B084CC",
    GASTRONOMY: "#F4B25E",
    CULTURE: "#8C6B4F",
    SOCIAL_EVENTS: "#E56B8A",
    TRANSPORTATION: "#6FA8DC",
};

const categoryNames: Record<string, string> = {
    STUDIES: "Studies",
    ACCOMMODATION: "Accommodation",
    DOCUMENTATION: "Documentation",
    PERSONAL_EXPERIENCE: "Personal Experience",
    GASTRONOMY: "Gastronomy",
    CULTURE: "Culture",
    SOCIAL_EVENTS: "Social Events",
    TRANSPORTATION: "Transportation",
};

const normalizeCategory = (cat: unknown) => {
    if (!cat && cat !== 0) return "";
    return String(cat).replace(/\s+/g, "_").replace(/-/g, "_").toUpperCase();
};

const defaultExperienceService = createExperienceService(API);

export default function DetailedExperiencePage({ experienceService = defaultExperienceService }: experienceServiceProps) {

    const [experience, setExperience] = useState<ExperienceDTO | null>(null);
    const { id } = useParams();

    const navigate = useNavigate();

    useEffect(() => {
        const fetchExperience = async () => {
            try {
                const data = await experienceService.getExperienceById(Number(id));
                if (data === null) navigate("/available-soon")
                setExperience(data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchExperience();
    }, [id, experienceService]);

    if (!experience) {
        return (
            <div className="container mx-auto max-w-7xl flex justify-center">
                <p>Loading experience...</p>
            </div>
        );
    }

    const rating = Math.min(10, Math.max(0, Number(experience.rating)));
    const red = Math.round((1 - rating / 10) * 255);
    const green = Math.round((rating / 10) * 255);
    const ratingColor = `rgb(${red}, ${green}, 0)`;

    return (
        <div className="container mx-auto max-w-7xl p-4 md:p-6">
            <div className="grid grid-cols-1 lg:grid-cols-[minmax(0,7fr)_minmax(18rem,3fr)] gap-6 items-stretch">

                <main className="w-full rounded-2xl bg-white shadow-xl p-6 md:p-8">
                    <div className="flex flex-col gap-6">

                        <div className="flex flex-col sm:flex-row items-start gap-4">
                            <img src="/images/available_soon.png" alt="User profile" className="w-16 h-16 md:w-20 md:h-20 rounded-full object-cover shrink-0" />

                            <div className="flex-1 min-w-0">
                                <div className="flex flex-wrap items-center gap-3">
                                    <h4>{experience.author.displayName}</h4>

                                    <div className="flex flex-wrap gap-2">
                                        {experience.categories?.map((category) => {
                                            const key = normalizeCategory(category);

                                            return (
                                                <span key={String(category)} className="text-white text-sm px-3 py-1 rounded-md" style={{ backgroundColor: categoryStyles[key] ?? "#7E8FA6" }}>
                                                    {categoryNames[key] ?? String(category)}
                                                </span>
                                            );
                                        })}
                                    </div>
                                </div>

                                <p className="mt-2">
                                    {experience.date}
                                    <span className="mx-2">•</span>
                                    {experience.city.name}, {experience.city.country}
                                </p>
                            </div>

                            <div className="flex flex-col items-center gap-3 shrink-0">
                                <div className="flex items-center justify-center w-14 h-14 rounded-xl text-white font-bold text-xl" style={{ backgroundColor: ratingColor }}>
                                    {rating.toFixed(1)}
                                </div>

                                <button type="button" aria-label="Share experience" className="w-11 h-11 flex items-center justify-center rounded-full p-2">
                                    ↗
                                </button>
                            </div>
                        </div>

                        <div>
                            <h3>{experience.title}</h3>
                        </div>

                        <div>
                            <p className="text-base md:text-lg leading-relaxed">{experience.description}</p>
                        </div>

                        <div className="flex flex-wrap justify-center items-center gap-6 mt-6">
                            <div className="w-full sm:w-[45%] flex justify-center">
                                <img src="/images/available_soon.png" alt="Multimedia" className="w-full max-w-2xl max-h-[35vh] h-auto object-contain rounded-2xl" />
                            </div>
                            <div className="w-full sm:w-[45%] flex justify-center">
                                <img src="/images/available_soon.png" alt="Multimedia" className="w-full max-w-2xl max-h-[35vh] h-auto object-contain rounded-2xl" />
                            </div>
                        </div>

                    </div>
                </main>

                <aside className="w-full rounded-2xl bg-white shadow-xl p-4 md:p-6 flex flex-col min-h-full">

                    <Comments experienceService={experienceService} experienceId={experience.id} />

                </aside>

            </div>
        </div>
    );
}