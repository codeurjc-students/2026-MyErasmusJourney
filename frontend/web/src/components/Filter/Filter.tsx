import type { cityServiceProps } from "@shared/interfaces/cityServiceProps";
import type { experienceServiceProps } from "@shared/interfaces/experienceServiceProps";
import type { CitySimpleDTO } from "@shared/models/CitySimpleDTO";
import type { ExperienceFilters } from "@shared/models/ExperienceFilters";
import { ApiError } from "@shared/api/apiError";
import { createCityService } from "@shared/services/city.service";
import { createExperienceService } from "@shared/services/experience.service";
import { useEffect, useState, type FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { API } from "../../api/client";
import "./Filter.css";
import { toast } from "sonner";

interface filterProps {
    setFilters: (filters: ExperienceFilters) => void;
}

export default function Filter({ setFilters, experienceService = createExperienceService(API), cityService = createCityService(API) }: filterProps & experienceServiceProps & cityServiceProps) {

    const [cities, setCities] = useState<CitySimpleDTO[]>([]);
    const [categories, setCategories] = useState<string[]>([]);
    const navigate = useNavigate();

    const categoryNames: Record<string, string> = {
        STUDIES: "Studies",
        ACCOMMODATION: "Accommodation",
        DOCUMENTATION: "Documentation",
        PERSONAL_EXPERIENCE: "Personal Experience",
        GASTRONOMY: "Gastronomy",
        CULTURE: "Culture",
        SOCIAL_EVENTS: "Social Events",
        TRANSPORTATION: "Transportation"
    };

    useEffect(() => {
        const fetchCities = async () => {
            try {
                const data = await cityService.getAll();
                setCities(data);
            } catch (error) {
                if (error instanceof ApiError && error.status >= 500) {
                    console.error(error);
                    navigate("/error");
                    return;
                }
                else if (error instanceof Error) {
                    toast.error(error.message);
                } else {
                    toast.error("An unexpected error occurred");
                }
            }
        };

        const fetchCategories = async () => {
            try {
                const data = await experienceService.getCategories();
                setCategories(data);
            } catch (error) {
                if (error instanceof ApiError && error.status >= 500) {
                    console.error(error);
                    navigate("/error");
                    return;
                }
                else if (error instanceof Error) {
                    toast.error(error.message);
                } else {
                    toast.error("An unexpected error occurred while fetching categories");
                }
            }
        };

        fetchCities();
        fetchCategories();
    }, []);

    function filterExperiences(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();

        const formData = new FormData(e.currentTarget);

        const minRatingValue = formData.get("minRating");
        const maxRatingValue = formData.get("maxRating");
        const cityValue = (formData.get("city") as string).trim();

        const minRating = minRatingValue ? Number(minRatingValue) : undefined;
        const maxRating = maxRatingValue ? Number(maxRatingValue) : undefined;
        const from = (formData.get("from") as string) || undefined;
        const to = (formData.get("to") as string) || undefined;
        const cityName = cityValue || undefined;
        const selectedCategories = formData.getAll("categories") as string[];

        setFilters({ minRating, maxRating, from, to, cityName, page: 0, categories: selectedCategories })
    }

    return (
        <form className="filter-panel" onSubmit={filterExperiences}>
            <h4 className="filter-title">Tailor Your Exploration</h4>

            <div className="filter-section">
                <h5>Rating</h5>

                <div className="filter-rating">
                    <div className="filter-field">
                        <label htmlFor="minRating">At least</label>
                        <input type="number" id="minRating" name="minRating" min="0" max="10" step="0.01" placeholder="0.00" />
                    </div>

                    <div className="filter-field">
                        <label htmlFor="maxRating">No more than</label>
                        <input type="number" id="maxRating" name="maxRating" min="0" max="10" step="0.01" placeholder="10.00" />
                    </div>
                </div>
            </div>

            <div className="filter-section">
                <h5>Date</h5>

                <div className="filter-dates">
                    <div className="filter-field">
                        <label htmlFor="from">From</label>
                        <input type="date" id="from" name="from" />
                    </div>

                    <div className="filter-field">
                        <label htmlFor="to">To</label>
                        <input type="date" id="to" name="to" />
                    </div>
                </div>
            </div>

            <div className="filter-section">
                <label htmlFor="city">City</label>

                <select id="city" name="city">
                    <option value="">All cities</option>

                    {cities.map(city => (
                        <option key={city.id} value={city.name}>{city.name}</option>
                    ))}
                </select>
            </div>

            <div className="filter-section">
                <label>Categories</label>

                <div className="filter-categories">
                    {categories.map(category => (
                        <label key={category} className="filter-category">
                            <input type="checkbox" name="categories" value={category} />
                            <span>{categoryNames[category] ?? category}</span>
                        </label>
                    ))}
                </div>
            </div>

            <button type="submit" className="filter-button">Find your next experiences</button>
        </form>
    );
}