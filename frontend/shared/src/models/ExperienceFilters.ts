export interface ExperienceFilters {
    minRating?: number;
    maxRating?: number;
    from?: string;
    to?: string;
    cityName?: string;
    categories?: string[];
    page?: number;
    size?: number;
}