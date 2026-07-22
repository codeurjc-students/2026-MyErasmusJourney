import { createUserService, type UserService } from "@shared/services/user.service";
import { API } from "../../api/client";
import type { FormEvent } from "react";
import "./SignUpPage.css";
import { Link, Navigate, useNavigate } from "react-router-dom";

interface SignUpPageProps {
    userService?: UserService;
}

export default function SignUpPage({userService = createUserService(API)}: SignUpPageProps) {

    const navigate = useNavigate();

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
            navigate("/");
        }
        catch(error){
            console.log(`Error signing up: ${error}`);
            alert(`Error signing up: ${error}`);
            return;
        }
    }

    return(<>
        <div className="container mx-auto max-w-4xl p-6 grid gap-10 items-center">
            <div className="row-span-1 title">
                <h3>Sign Up</h3>
            </div>
            <div className="row-span-1 grid grid-cols-1 gap-6 md:grid-cols-2 items-start">
                <div className="col-span-1 md:col-span-1">
                    <form onSubmit={handleSubmit} className="flex flex-col gap-4">
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
                        <button type="submit" className="mx-auto mt-4">Sign Up</button>
                    </form>
                </div>
                <div className="col-span-1 md:col-span-1 flex justify-center">
                    <img className="max-w-full h-auto profileImage" src="/images/available_soon.png" alt="opened book with a quill inside a compass"/>
                </div>
            </div>
            <div className="row-span-1 flex justify-center">
                <p>Do you have an account? <Link to={"/"} className="link">Log in →</Link></p>
            </div>
        </div>
    </>)
}