import type { authServiceProps } from "@shared/interfaces/authServiceProps";
import type { FormEvent } from "react";
import { API } from "../../api/client";
import { createAuthService } from "@shared/services/auth.service";
import "./LogInPage.css";
import { Link, useNavigate } from "react-router-dom";


export default function LogInPage({authService = createAuthService(API)}: authServiceProps){

    const navigate = useNavigate();

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();

        const form = event.currentTarget;
        const formData = new FormData(form);

        const username = formData.get("email") as string;
        const password = formData.get("password") as string;

        console.log(username);
        if (username === ""){
            alert("Email missing");
            return;
        }

        if (password === "") {
            alert("Passwords needed");
            return;
        }

        const loginRequest = {
            username,
            password
        };
        
        try{
            await authService.logIn(loginRequest);
            navigate("/");
        }
        catch(error){
            console.log(`Error logging in: ${error}`);
            alert(`Error logging in: ${error}`);
            return;
        }
    }

    return(<>
        <div className="container mx-auto max-w-4xl p-6 grid gap-10 items-center">
            <div className="row-span-1 title">
                <h3>Log In</h3>
            </div>
            <div className="row-span-1">
                <form onSubmit={handleSubmit} className="flex flex-col gap-4">
                    <label htmlFor="email">Email</label>
                    <input type="email" id="email" name="email" required />
                    <label htmlFor="password">Password</label>
                    <input type="password" id="password" name="password" required />
                    <button type="submit" className="mx-auto mt-4">Sign Up</button>
                </form>
            </div>
             <div className="row-span-1 flex justify-center">
                <p>Don't have an account yet? <Link to={"/sign-up"} className="link">Sign up →</Link></p>
            </div>
        </div>
    </>)
}