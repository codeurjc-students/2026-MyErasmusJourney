import type { ExperienceFilters } from "@shared/models/ExperienceFilters";
import type { ExperienceSimpleDTO } from "@shared/models/ExperienceSimpleDTO";
import type { experienceServiceProps } from "@shared/interfaces/experienceServiceProps";
import { useEffect, useState } from "react";
import Experience from "../../components/Experience/Experience";
import { API } from "../../api/client";
import { createExperienceService } from "@shared/services/experience.service";
import { ApiError } from "@shared/api/apiError";
import { useNavigate } from "react-router-dom";
import Filter from "../../components/Filter/Filter";
import type { cityServiceProps } from "@shared/interfaces/cityServiceProps";
import { createCityService } from "@shared/services/city.service";

const defaultExperienceService = createExperienceService(API);

export default function ExperiencesPage({ experienceService = defaultExperienceService, cityService = createCityService(API) }: experienceServiceProps & cityServiceProps) {

    const [experiences, setExperiences] = useState<ExperienceSimpleDTO[]>([]);
    const [filters, setFilters] = useState<ExperienceFilters>({ page: 0 });
    const [totalPages, setTotalPages] = useState(1);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await experienceService.getAll(filters);
                setExperiences(data.content || []);
                setTotalPages(data.page.totalPages ?? 1);
            } catch (error) {
                if (error instanceof ApiError && error.status >= 500) {
                    console.error(error);
                    navigate("/error");
                    return;
                }
                console.error(error);
            }
        };

        fetchData();
    }, [experienceService, filters]);

    const hasPreviousPage = (filters.page ?? 0) > 0;
    const hasNextPage = (filters.page ?? 0) < totalPages - 1;

    function handlePreviousPage() {
        if (hasPreviousPage) {
            setFilters(currentFilters => ({
                ...currentFilters,
                page: (currentFilters.page ?? 0) - 1
            }));
        }
    }

    function handleNextPage() {
        if (hasNextPage) {
            setFilters(currentFilters => ({
                ...currentFilters,
                page: (currentFilters.page ?? 0) + 1
            }));
        }
    }

    return (
        <div id="experiences" className="mx-auto w-[97%] max-w-none p-4 md:p-6">
            <div className="grid grid-cols-1 lg:grid-cols-[minmax(27rem,1fr)_minmax(0,4fr)] gap-6 items-stretch">
                <div className="w-full rounded-2xl bg-white shadow-xl p-5 md:p-6">
                    <Filter setFilters={setFilters} experienceService={experienceService} cityService={cityService}/>
                </div>

                <main className="w-full rounded-2xl bg-white shadow-xl p-4 md:p-6">
                    <h3 className="text-center mb-6">Experiences</h3>

                    <div className="grid grid-cols-1 sm:grid-cols-2 gap-5">
                        {experiences.map(experience => (
                            <Experience key={experience.id} experience={experience} />
                        ))}
                    </div>

                    <div className="flex justify-center items-center gap-6 mt-8">
                        <button type="button" onClick={handlePreviousPage} disabled={!hasPreviousPage} className="disabled:opacity-40 disabled:cursor-not-allowed">
                            Previous
                        </button>

                        <p>Page {(filters.page ?? 0) + 1} of {totalPages}</p>

                        <button type="button" onClick={handleNextPage} disabled={!hasNextPage} className="disabled:opacity-40 disabled:cursor-not-allowed">
                            Next
                        </button>
                    </div>
                </main>
            </div>
        </div>
    );
}