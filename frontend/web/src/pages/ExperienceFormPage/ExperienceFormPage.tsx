import type { cityServiceProps } from "@shared/interfaces/cityServiceProps";
import type { experienceServiceProps } from "@shared/interfaces/experienceServiceProps";
import type { CitySimpleDTO } from "@shared/models/CitySimpleDTO";
import type { ExperienceFormDTO } from "@shared/models/ExperienceFormDTO";

import { createCityService } from "@shared/services/city.service";
import { createExperienceService } from "@shared/services/experience.service";
import { useUserStore } from "@shared/stores/userStore";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { API } from "../../api/client";
import { ApiError } from "@shared/api/apiError";
import { toast } from "sonner";

/**
 * Page with the form to publish a new experience, including optional
 * multimedia (photos and videos).
 * The services can be injected (used by the integration tests); by default
 * they use the real API client.
 */
export default function ExperienceFormPage({ experienceService = createExperienceService(API), cityService = createCityService(API) }: experienceServiceProps & cityServiceProps) {

    const [categories, setCategories] = useState<string[]>([]);

    const [cities, setCities] = useState<CitySimpleDTO[]>([]);

    const { user } = useUserStore();

    const navigate = useNavigate();

    // On mount: redirect to login if nobody is authenticated and load the
    // categories and cities that populate the form.
    useEffect(() => {
        if (user === null) {
            navigate("/log-in");
        }

        const fetchCategories = async () => {
            try {
                const data = await experienceService.getCategories();
                setCategories(data)
            }
            catch (error) {
                console.error(error)
            }
        }

        const fetchCities = async () => {
            try {
                const data = await cityService.getAll();
                setCities(data)
            }
            catch (error) {
                console.error(error)
            }
        }
        fetchCategories();
        fetchCities();
    }, [])

    /** Turns a backend category (e.g. "FOOD_AND_DRINK") into a readable label. */
    function formatCategory(category: string): string {
        return category.replace(/_/g, " ");
    }

    /**
     * Submits the form:
     * 1. Validates that at most 3 categories are selected.
     * 2. Creates the experience.
     * 3. Uploads the selected multimedia files, if any.
     * 4. Navigates to the new experience's detail page.
     * Errors: 5xx navigates to the error page, 415 warns about the unsupported
     * file format, anything else shows a toast with the error message.
     */
    async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault();

        const formData = new FormData(event.currentTarget);

        const title = formData.get("title") as string;
        const description = formData.get("description") as string;
        const date = formData.get("date") as string;
        const cityId = Number(formData.get("location") as string);
        const rating = Number(formData.get("rating") as string)
        const categories = formData.getAll("categories") as string[];
        const multimediaInput = event.currentTarget.elements.namedItem("multimedia") as HTMLInputElement;
        const files = multimediaInput.files
            ? Array.from(multimediaInput.files)
            : [];

        if (categories.length > 3) {
            toast.warning("No more than 3 categories are allowed for an experience");
            return;
        }

        const experienceRequest: ExperienceFormDTO = {
            title,
            description,
            date,
            rating,
            cityId,
            categories
        }

        for (const file of files) {
            if(file.size > 16 * 1024 * 1024) { 
                toast.warning("File size exceeds the 16MB limit. Please upload smaller files.");
                return;
            }
        }

        try {
            const newExperience = await experienceService.postExperience(experienceRequest);

            if (files.length > 0) {
                await experienceService.addMultimedia(files, newExperience.id);
            }
            navigate(`/experiences/${newExperience.id}`)
        }
        catch (error) {
            if (error instanceof ApiError && error.status >= 500) {
                console.error(error);
                navigate("/error");
                return;
            }
            else if (error instanceof ApiError && error.status === 415) {
                toast.error("Unsupported file format. Please upload images (JPEG, PNG, GIF) or videos (MP4, WebM, QuickTime, AVI, MKV).");
            }
            else if (error instanceof Error) {
                toast.error(error.message);
            } else {
                toast.error("Error while publishing your experience.");
            }
        }

    }

    return (<>
        <div className="container mx-auto max-w-6xl rounded-3xl bg-white shadow-2xl p-6 md:p-10">

            <h3 id="experienceFormTitle" className="text-center mb-10">New Experience</h3>

            <form onSubmit={handleSubmit} className="mx-auto grid grid-cols-1 md:grid-cols-2 gap-x-8 gap-y-6">
                <div className="flex flex-col gap-6">

                    <div className="grid grid-cols-1 sm:grid-cols-4 gap-6">

                        <div className="flex flex-col gap-3 sm:col-span-3">
                            <label htmlFor="title">Title</label>
                            <input type="text" id="title" name="title" required />
                        </div>
                        <div className="flex flex-col gap-3 sm:col-span-1">
                            <label htmlFor="rating">Rating</label>
                            <input type="number" id="rating" name="rating" min="0" max="10" step="0.1" required />
                        </div>

                    </div>

                    <div className="flex flex-col gap-3">
                        <label>Category</label>
                        <div className="grid grid-cols-2 sm:grid-cols-3 gap-x-6 gap-y-3">
                            {categories.map(category => (
                                <label key={category} className="flex items-center gap-2">
                                    <input type="checkbox" name="categories" value={category} />{formatCategory(category)}
                                </label>
                            ))}
                        </div>
                    </div>

                    <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
                        <div className="flex flex-col gap-3">
                            <label htmlFor="location">Location</label>
                            <select id="location" name="location" required>
                                {cities.map(city => (
                                    <option key={city.id} value={city.id}>{city.name}, {city.country}</option>
                                ))}
                            </select>
                        </div>
                        <div className="flex flex-col gap-3">
                            <label htmlFor="date">Date</label>
                            <input type="date" id="date" name="date" />
                        </div>
                    </div>

                    <div className="flex flex-col gap-3">
                        <label htmlFor="description">Experience Description</label>
                        <textarea id="description" name="description" rows={6} required className="resize-none" />
                    </div>

                    <div className="flex justify-center mt-2">
                        <button type="submit">Publish</button>
                    </div>

                </div>

                <div className="flex flex-col items-center justify-center gap-6">
                    <div className="flex justify-center items-center w-full flex-1">
                        <input type="file" id="multimedia" name="multimedia" accept="image/jpeg, image/png, image/gif, video/mp4, video/webm, video/quicktime, video/x-msvideo, video/x-matroska" multiple />
                    </div>
                    <p>Add Multimedia</p>
                </div>

            </form>
        </div>
    </>)
}
