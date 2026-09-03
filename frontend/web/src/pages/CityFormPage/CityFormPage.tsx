import { createCityService } from "@shared/services/city.service";
import type { cityServiceProps } from "@shared/interfaces/cityServiceProps";
import { API } from "../../api/client";
import type { CityFormDTO } from "@shared/models/CityFormDTO";
import { useNavigate } from "react-router-dom";
import type { FormEvent } from "react";
import { ApiError } from "@shared/api/apiError";

export default function CityFormPage({ cityService = createCityService(API) }: cityServiceProps) {


    const navigate = useNavigate();

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();

        const form = event.currentTarget;
        const formData = new FormData(form);

        const name = formData.get("city") as string;
        const country = formData.get("country") as string;
        const description = formData.get("description") as string;



        const cityFormDTO: CityFormDTO = {
            name,
            country,
            description
        };

        try {
            await cityService.addCity(cityFormDTO);
            navigate("/available-soon");
        }
        catch (error) {
            if (error instanceof ApiError && error.status >= 500) {
                navigate("/error");
                return;
            }
            console.log(error);
            alert(error);
            return;
        }
    }

    return (
        <div className="container mx-auto max-w-6xl rounded-3xl bg-white shadow-2xl p-6 md:p-10">

            <h3 className="text-center mb-10">New City</h3>

            <form onSubmit={handleSubmit} className="mx-auto grid grid-cols-1 md:grid-cols-2 gap-x-8 gap-y-8">
                <div className="w-full flex flex-col gap-4">
                    <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
                        <div className="flex flex-col gap-3">
                            <label htmlFor="city">City Name</label>
                            <input type="text" id="city" name="city" required />
                        </div>
                        <div className="flex flex-col gap-3">
                            <label htmlFor="country">Country</label>
                            <input type="text" id="country" name="country" required />
                        </div>
                    </div>
                    <div className="flex flex-col gap-3 mt-4">
                        <label htmlFor="description">City Description</label>
                        <textarea id="description" name="description" rows={6} placeholder="What should people know about the city?" required className="resize-none" />
                    </div>
                    <div className="flex justify-center mt-4">
                        <button type="submit">Save City
                        </button>
                    </div>
                </div>

                <div className="w-full flex flex-col gap-3 items-center">
                    <label htmlFor="mapPreview">
                        Preview
                    </label>
                    <div className="w-full flex justify-center overflow-hidden rounded-2xl">
                        <img id="mapPreview" src="/images/available_soon.png" alt="City map preview" className="w-auto max-h-90 object-cover rounded-2xl" />
                    </div>
                </div>
            </form>
        </div>
    );
}