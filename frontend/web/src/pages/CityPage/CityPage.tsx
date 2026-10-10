import { API } from "@/api/client";
import Experience from "@/components/Experience/Experience";
import { ApiError } from "@shared/api/apiError";
import type { cityServiceProps } from "@shared/interfaces/cityServiceProps";
import type { CityDTO } from "@shared/models/CityDTO";
import { createCityService } from "@shared/services/city.service";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

export default function CityPage({ cityService = createCityService(API) }: cityServiceProps) {
    const [city, setCity] = useState<CityDTO | null>(null);
    const { id } = useParams();
    const navigate = useNavigate();

    const rating = Math.min(10, Math.max(0, Number(city?.averageRating ?? 0)));
    const red = Math.round((1 - rating / 10) * 255);
    const green = Math.round((rating / 10) * 255);
    const ratingColor = `rgb(${red}, ${green}, 0)`;

    useEffect(() => {
        const fetchCity = async () => {
            try {
                const data = await cityService.getCityById(Number(id));
                setCity(data);
            } catch (error) {
                if (error instanceof ApiError && (error.status >= 500 || error.status === 404)) {
                    console.error(error);
                    navigate("/error");
                    return;
                }

                console.error(error);
            }
        };

        fetchCity();
    }, [id]);

    return (
        <div className="mx-auto w-[97%] max-w-none p-4 md:p-6">
            <div className="grid grid-cols-1 lg:grid-cols-[minmax(0,7fr)_minmax(0,3fr)] gap-6 items-stretch">
                <main className="w-full min-w-0 rounded-2xl bg-white shadow-xl p-4 md:p-6 flex flex-col">
                    <h3 className="text-center mb-4 md:mb-6 break-words">
                        {city?.name ?? "City"}
                    </h3>

                    <div className="w-full flex items-center justify-center">
                        <img src="/images/available_soon.png" alt="Available Soon" className="mainImage max-w-sm w-3/4 h-auto object-contain" />
                    </div>

                    <p className="mt-4 md:mt-6 text-base md:text-lg leading-relaxed">
                        {city?.description}
                    </p>
                </main>

                <aside className="w-full min-w-0 rounded-2xl bg-white shadow-xl p-4 md:p-6">
                    <div className="flex flex-wrap items-center justify-between gap-3 mb-6">
                        <h4 className="m-0 text-xl md:text-2xl font-bold text-[#1E3A5F]">Students Ratings</h4>
                        <div className="flex items-center justify-center shrink-0 w-12 h-12 rounded-xl text-white font-bold text-lg" style={{ backgroundColor: ratingColor }}>
                            {rating.toFixed(1)}
                        </div>
                            {city?.experiences.map(experience => (
                                <Experience key={experience.id} experience={experience} />
                            ))}
                    </div>
                </aside>
            </div>
        </div>
    );
}