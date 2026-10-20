import type { UserSimpleDTO } from "./UserSimpleDTO";

export interface UserImageDTO{
    id: number;
    contentType: string;
    user: UserSimpleDTO;
}