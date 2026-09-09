import type { UserSimpleDTO } from "./UserSimpleDTO";
import type { ExperienceSimpleDTO } from "./ExperienceSimpleDTO";

export interface CommentDTO{
    id: number;
    description: string;
    date: string;
    author: UserSimpleDTO;
    experience: ExperienceSimpleDTO;
}