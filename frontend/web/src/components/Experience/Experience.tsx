import type { ExperienceSimpleDTO } from "@shared/models/ExperienceSimpleDTO.ts";
import { Link } from "react-router-dom";
import "./Experience.css";

interface ExperienceProps {
    experience: ExperienceSimpleDTO;
}

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

export default function Experience({ experience }: ExperienceProps) {

    const rating = Math.min(10, Math.max(0, Number(experience.rating)));
    const red = Math.round((1 - rating / 10) * 255);
    const green = Math.round((rating / 10) * 255);
    const ratingColor = `rgb(${red}, ${green}, 0)`;

    const description = experience.description ?? "";
    const shortDescription = description.length > 80 ? `${description.substring(0, 80)}...` : description;

    return (
        <div id={`experience-${experience.id}`} className="experience-card w-full rounded-2xl bg-white shadow-md p-4 transition hover:shadow-lg flex flex-col gap-3">
            <div className="flex flex-row items-start gap-4">
                <div className="flex items-start gap-3 shrink-0">
                    <img src="/images/available_soon.png" alt="User profile" className="w-12 h-12 md:w-24 md:h-24 rounded-full object-cover" />
                </div>

                <div className="flex-1 min-w-0">
                    <div className="flex items-start justify-between gap-3">
                        <div className="min-w-0 flex-1">
                            <Link to={`/experiences/${experience.id}`} className="block font-bold text-base underline">
                                {experience.title}
                            </Link>
                            <p className="exp-meta mb-0">by <span className="underline exp-meta">{experience.authorName}</span></p>
                        </div>

                        <div className="shrink-0 flex items-center justify-center w-12 h-12 rounded-xl text-white font-bold exp-rating" style={{ backgroundColor: ratingColor }}>
                            <span className="exp-rating-text">{rating.toFixed(1)}</span>
                        </div>
                    </div>

                    <div className="mt-2">
                        <p className="exp-meta">
                            {experience.cityName || experience.country ? (
                                <>
                                    {experience.cityName}{experience.cityName && experience.country ? ', ' : ''}{experience.country}
                                    {experience.date ? (<span className="mx-1">·</span>) : null}
                                </>
                            ) : null}
                            {experience.date}
                        </p>
                    </div>

                    <div className="mt-3 categories-row">
                        {experience.categories?.map((category) => {
                            const key = normalizeCategory(category);
                            return (
                                <span key={String(category)} className="exp-category text-white text-xs sm:text-sm px-2 sm:px-3 py-1 rounded-md" style={{ backgroundColor: categoryStyles[key] ?? "#7E8FA6" }}>
                                    {categoryNames[key] ?? String(category)}
                                </span>
                            );
                        })}
                    </div>
                </div>
            </div>

            {/* Description spans full card width below the image and header */}
            <div className="mt-1 w-full">
                <p className="text-sm exp-description">
                    {shortDescription}{" "}
                    {description.length > 80 && (
                        <Link to={`/experiences/${experience.id}`} className="link underline">Read More →</Link>
                    )}
                </p>
            </div>
        </div>
    );
}