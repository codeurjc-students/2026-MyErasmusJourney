import {create} from "zustand";
import type { UserSimpleDTO } from "../models/UserSimpleDTO";

interface UserStore{
    user: UserSimpleDTO | null;
    setUser: (user: UserSimpleDTO | null) => void;
}

export const useUserStore = create<UserStore>((set) => ({
    user: null,
    setUser: (user) => set({user})
}))