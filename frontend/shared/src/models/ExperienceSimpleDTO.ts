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
    totalElements?: number;
    totalPages?: number;
    number?: number;
    size?: number;
}