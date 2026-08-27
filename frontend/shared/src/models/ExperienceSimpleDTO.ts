import type {Page} from "./Page";

export interface ExperienceSimpleDTO{
    id: number;
    title: string;
    date: string;
    rating: number;
    description: string;
    categories: string[]
    cityName: string;
    country: string;
    authorName: string;
}

export interface ExperiencePageDTO {
    content: ExperienceSimpleDTO[];
    page: Page
}