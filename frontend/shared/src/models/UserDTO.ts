import type { CommentSimpleDTO } from "./CommentSimpleDTO";
import type { ExperienceSimpleDTO } from "./ExperienceSimpleDTO";

export interface UserDTO{
    id: number;
    displayName: String;
    fullName: String;
    email: String;
    studyLocation: String;
    roles: Array<String>;
    experiences: ExperienceSimpleDTO[];
    comments: CommentSimpleDTO[];
}