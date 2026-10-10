import type { ExperienceSimpleDTO } from "./ExperienceSimpleDTO";

export interface CityDTO {
    id: number;
    name: string;
    country: string;
    description: string;
    averageRating: number;
    experiences: ExperienceSimpleDTO[];
}