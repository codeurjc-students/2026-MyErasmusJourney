import type { cityServiceProps } from "@shared/interfaces/cityServiceProps";
import type { experienceServiceProps } from "@shared/interfaces/experienceServiceProps";
import type {CitySimpleDTO} from "@shared/models/CitySimpleDTO";
import type {ExperienceFormDTO} from "@shared/models/ExperienceFormDTO";


import { createCityService } from "@shared/services/city.service";
import { createExperienceService } from "@shared/services/experience.service";
import { useUserStore } from "@shared/stores/userStore";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { API } from "../../api/client";

export default function ExperienceFormPage({ experienceService = createExperienceService(API), cityService = createCityService(API) }: experienceServiceProps & cityServiceProps ){

    const [categories, setCategories] = useState<String[]>([]);
    
    const [cities, setCities] = useState<CitySimpleDTO[]>([]);

    const { user } = useUserStore();

    const navigate = useNavigate();

    useEffect(() => {
        if (user === null){
            navigate("/log-in");
        }

        const fetchCategories = async () => {
            try{
                const data = await experienceService.getCategories();
                setCategories(data)
            }
            catch(error){
                console.error(error)
            }
        }

        const fetchCities = async () => {
            try{
                const data = await cityService.getAll();
                setCities(data)
            }
            catch(error){
                console.error(error)
            }
        }
        fetchCategories();
        fetchCities();
    }, [])

    function formatCategory(category: String): String {
        return category.replace(/_/g, " ");
    }

    async function handleSubmit(event: React.FormEvent<HTMLFormElement>){
        event.preventDefault();

        const form = event.currentTarget;
        const formData = new FormData(form);

        const title = formData.get("title") as string;
        const description = formData.get("description") as string;
        const date = formData.get("date") as string;
        const cityId = Number(formData.get("location") as string);
        const rating = Number(formData.get("rating") as string)
        const categories = formData.getAll("categories") as string[];

        if (categories.length > 3){
            alert("No more than 3 categories are allowed for an experience");
            return;
        }

        const experienceRequest: ExperienceFormDTO ={
            title,
            description,
            date,
            rating,
            cityId,
            categories
        }

        try{
            await experienceService.postExperience(experienceRequest);
            navigate("/available-soon")
        }
        catch(error){
            alert("Error while publishing your experience.");
            console.log(error);
        }
        
    }
    
    return(<>
        <div className="container mx-auto max-w-6xl rounded-3xl bg-white shadow-2xl p-6 md:p-10">

        <h3 className="text-center mb-10">New Experience</h3>

        <form onSubmit={handleSubmit} className="mx-auto grid grid-cols-1 md:grid-cols-2 gap-x-8 gap-y-6">
            <div className="flex flex-col gap-6">

                <div className="grid grid-cols-1 sm:grid-cols-4 gap-6">

                    <div className="flex flex-col gap-3 sm:col-span-3">
                        <label htmlFor="title">Title</label>
                        <input type="text" id="title" name="title" required />
                    </div>
                    <div className="flex flex-col gap-3 sm:col-span-1">
                        <label htmlFor="rating">Rating</label>
                        <input type="number" id="rating" name="rating" min="0" max="10" step="0.1" required/>
                    </div>

                </div>

                <div className="flex flex-col gap-3">
                    <label>Category</label>
                    <div className="grid grid-cols-2 sm:grid-cols-3 gap-x-6 gap-y-3">
                        {categories.map(category => (
                            <label className="flex items-center gap-2">
                                <input type="checkbox" name="categories" value={`${category}`}/>{formatCategory(category)}
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
                        <input type="date" id="date" name="date"/>
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
                    <img src="/images/available_soon.png" alt="Add multimedia" className="w-3/4 max-w-sm h-auto object-contain"/>
                </div>
                <p>Add Multimedia</p>
            </div>

        </form>
    </div>
    </>)
}