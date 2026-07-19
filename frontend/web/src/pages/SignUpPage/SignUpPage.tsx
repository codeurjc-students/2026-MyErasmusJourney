import { createUserService, type UserService } from "@shared/services/user.service";
import { API } from "../../api/client";
import type { FormEvent } from "react";

interface SignUpPageProps {
    userService?: UserService;
}

export default function SignUpPage({userService = createUserService(API)}: SignUpPageProps) {

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();

        const form = event.currentTarget;
        const formData = new FormData(form);

        const fullName = formData.get("fullName") as string;
        const displayName = formData.get("displayName") as string;
        const email = formData.get("email") as string;
        const password = formData.get("password") as string;
        const passwordConfirmation = formData.get("passwordConfirmation") as string;

        if (password !== passwordConfirmation) {
            alert("Passwords do not match");
            return;
        }

        const userFormDTO = {
            fullName,
            displayName,
            email,
            password,
            passwordConfirmation
        };
        
        try{
            await userService.signUp(userFormDTO);
            console.log("User signed up successfully");
            window.location.href = "/";

        }
        catch(error){
            console.log(`Error signing up: ${error}`);
            alert(`Error signing up: ${error}`);
            return;
        }
    }

    return(<>
        <div>
            <form onSubmit={handleSubmit}>
                <label htmlFor="fullName">Full name</label>
                <input type="text" id="fullName" name="fullName" required />
                <label htmlFor="displayName">Public name</label>
                <input type="text" id="displayName" name="displayName" required />
                <label htmlFor="email">Email</label>
                <input type="email" id="email" name="email" required />
                <label htmlFor="password">Password</label>
                <input type="password" id="password" name="password" required />
                <label htmlFor="passwordConfirmation">Repeat Password</label>
                <input type="password" id="passwordConfirmation" name="passwordConfirmation" required />
                <button type="submit">Sign Up</button>
            </form>
        </div>
    </>)
}