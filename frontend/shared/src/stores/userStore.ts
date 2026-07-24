import {create} from "zustand";
import { UserSimpleDTO } from "../models/UserSImpleDTO";

interface UserStore{
    user: UserSimpleDTO | null;
    setUser: (user: UserSimpleDTO | null) => void;
}

export const useUserStore = create<UserStore>((set) => ({
    user: null,
    setUser: (user) => set({user})
}))