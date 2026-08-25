import type { CitySimpleDTO } from "./CitySimpleDTO";
import type { UserSimpleDTO } from "./UserSimpleDTO";

export interface ExperienceDTO{
    id: number;
    title: string;
    date: string;
    rating: number;
    description: string;
    categories: string[];
    city: CitySimpleDTO;
    author: UserSimpleDTO;
}