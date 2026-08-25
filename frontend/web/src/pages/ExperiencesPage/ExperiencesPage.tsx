import { useEffect, useState } from "react";
import Experience from "../../components/Experience";
import { API } from "../../api/client";
import type { ExperienceSimpleDTO } from "@shared/models/ExperienceSimpleDTO";
import { createExperienceService } from "@shared/services/experience.service";
import type { experienceServiceProps } from "@shared/interfaces/experienceServiceProps";

export default function ExperiencesPage({ experienceService = createExperienceService(API) }: experienceServiceProps) {

    const [experiences, setExperiences] = useState<ExperienceSimpleDTO[]>([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);

    const size = 9;

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await experienceService.getAll(page, size);

                setExperiences(data.content || []);
                setTotalPages(data.totalPages ?? 1);
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
    }, [experienceService, page]);

    const hasPreviousPage = page > 0;
    const hasNextPage = page < totalPages - 1;

    function handlePreviousPage() {
        if (hasPreviousPage) {
            setPage((currentPage) => currentPage - 1);
        }
    }

    function handleNextPage() {
        if (hasNextPage) {
            setPage((currentPage) => currentPage + 1);
        }
    }

    return (
        <div id="experiences" className="mx-auto max-w-screen-2xl p-4 md:p-6">
            <div className="grid grid-cols-1 lg:grid-cols-[minmax(17rem,1fr)_minmax(0,13fr)] gap-6 items-stretch">

                <div className="w-full rounded-2xl bg-white shadow-xl p-6 flex items-center justify-center">
                    <img src="/images/available-soon.png" alt="Experience filter" className="w-full h-auto object-contain" />
                </div>

                <main className="w-full rounded-2xl bg-white shadow-xl p-4 md:p-6">
                    <h3 className="text-center mb-6">Experiences</h3>

                    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5">
                        {experiences.map((experience) => (
                            <Experience key={experience.id} experience={experience} />
                        ))}
                    </div>

                    <div className="flex justify-center items-center gap-6 mt-8">
                        <button type="button" onClick={handlePreviousPage} disabled={!hasPreviousPage} className="disabled:opacity-40 disabled:cursor-not-allowed">
                            Previous
                        </button>

                        <p>Page {page + 1} of {totalPages}</p>

                        <button type="button" onClick={handleNextPage} disabled={!hasNextPage} className="disabled:opacity-40 disabled:cursor-not-allowed">
                            Next
                        </button>
                    </div>
                </main>

            </div>
        </div>
    );
}