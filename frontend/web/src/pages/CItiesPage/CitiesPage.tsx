import { API } from "@/api/client";
import { ApiError } from "@shared/api/apiError";
import type { cityServiceProps } from "@shared/interfaces/cityServiceProps";
import type { CitySimpleDTO } from "@shared/models/CitySimpleDTO";
import { createCityService } from "@shared/services/city.service";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";



export default function CitiesPage({ cityService = createCityService(API) }: cityServiceProps) {

    const [trendingCities, setTrendingCities] = useState<CitySimpleDTO[]>([]);

    const navigate = useNavigate();

    const medalImages = ["/images/firstIcon.png", "/images/secondIcon.png", "/images/thirdIcon.png"];

    useEffect(() => {
        const fetchCities = async () => {
            try {
                const citiesData = await cityService.getTrending();
                setTrendingCities(citiesData);
            } catch (error) {
                if (error instanceof ApiError && ((error.status >= 500) || (error.status === 404))) {
                    console.error(error);
                    navigate("/error");
                    return;
                }
            }
        }

        fetchCities();
    }, [])


    return (
        <div className="mx-auto w-[97%] max-w-none p-4 md:p-6">
            <div className="grid grid-cols-1 lg:grid-cols-[3fr_1fr] gap-6 items-stretch">
                <main className="w-full rounded-2xl bg-white shadow-xl p-4 md:p-6">
                    <h3 className="text-center mb-6">Cities</h3>
                    <img src="/images/available_soon.png" alt="Available Soon" className="mainImage max-w-sm w-3/4 h-auto" />
                </main>

                <aside className="w-full rounded-2xl bg-white shadow-xl p-5 md:p-6">
                    <h4 className="text-center lg:text-left mb-5">Trending Destinations</h4>

                    <div className="flex flex-col gap-3">
                        {trendingCities.map((city, index) => (
                            <div key={city.id} className="flex items-center gap-3 w-full rounded-2xl bg-[#F7FBFF] px-4 py-3 shadow-md transition hover:shadow-lg">
                                <div className="flex items-center justify-center w-10 h-10 shrink-0">
                                    {index < 3 ? (
                                        <img src={medalImages[index]} alt={`${index + 1} place`} className="w-full h-full object-contain" />
                                    ) : (
                                        <span className="font-bold text-[#4A90D9]">{index + 1}</span>
                                    )}
                                </div>

                                <p className="flex-1 min-w-0 m-0 font-semibold text-[#1E3A5F] text-base md:text-lg truncate">
                                    {city.name}
                                </p>

                                <div className="flex items-center justify-center w-10 h-10 shrink-0 rounded-full bg-[#DCEFFD]">
                                    <span className="text-lg">🌍</span>
                                </div>
                            </div>
                        ))}
                    </div>
                </aside>
            </div>
        </div>
    );
}