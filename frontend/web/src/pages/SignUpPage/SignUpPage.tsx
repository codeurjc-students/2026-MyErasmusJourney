import { createUserService } from "@shared/services/user.service";
import type { userServiceProps } from "@shared/interfaces/userServiceProps";
import { API } from "../../api/client";
import type { FormEvent } from "react";
import "./SignUpPage.css";
import { Link, useNavigate } from "react-router-dom";
import { ApiError } from "@shared/api/apiError";


export default function SignUpPage({ userService = createUserService(API) }: userServiceProps) {

    const navigate = useNavigate();

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();

        const form = event.currentTarget;
        const formData = new FormData(form);

        const fullName = formData.get("fullName") as string;
        const displayName = formData.get("displayName") as string;
        const email = formData.get("email") as string;
        const city = (formData.get("city") as string).trim() || null;
        const country = (formData.get("country") as string).trim() || null;
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
            city,
            country,
            password,
            passwordConfirmation
        };

        try {
            await userService.signUp(userFormDTO);
            console.log("User signed up successfully");
            navigate("/log-in");
        }
        catch (error) {
            if (error instanceof ApiError && error.status >= 500) {
                console.error(error);
                navigate("/error");
                return;
            }
            console.log(`Error signing up: ${error}`);
            alert(`Error signing up: ${error}`);
            return;
        }
    }

    return (
        <>
            <div className="container mx-auto max-w-4xl p-6 grid gap-10 items-center">

                <div className="row-span-1 title">
                    <h3 id="signUpTitle">Sign Up</h3>
                </div>

                <div className="row-span-1">

                    <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-2 gap-x-8 gap-y-4">
                        {/* FULL NAME */}
                        <div className="flex flex-col gap-2">
                            <label htmlFor="fullName">Full name</label>
                            <input type="text" id="fullName" name="fullName" required />
                        </div>

                        {/* IMAGEN */}
                        <div className="md:row-span-5 flex justify-center items-center">
                            <img className="w-3/4 max-w-sm h-auto profileImg" src="/images/available_soon.png" alt="opened book with a quill inside a compass" />
                        </div>

                        {/* PUBLIC NAME */}
                        <div className="flex flex-col gap-2">
                            <label htmlFor="displayName">Public name</label>
                            <input type="text" id="displayName" name="displayName" required />
                        </div>

                        {/* CITY */}
                        <div className="flex flex-col gap-2">
                            <label htmlFor="city">Destination City</label>
                            <input type="text" id="city" name="city" />
                        </div>

                        {/* COUNTRY */}
                        <div className="flex flex-col gap-2">
                            <label htmlFor="country">Destination Country</label>
                            <input type="text" id="country" name="country" />
                        </div>

                        {/* EMAIL */}
                        <div className="flex flex-col gap-2">
                            <label htmlFor="email">Email</label>
                            <input type="email" id="email" name="email" required />
                        </div>

                        {/* PASSWORD */}
                        <div className="flex flex-col gap-2">
                            <label htmlFor="password">Password</label>
                            <input type="password" id="password" name="password" required />
                        </div>

                        {/* REPEAT PASSWORD */}
                        <div className="flex flex-col gap-2">
                            <label htmlFor="passwordConfirmation">Repeat Password</label>
                            <input type="password" id="passwordConfirmation" name="passwordConfirmation" required />
                        </div>

                        {/* SIGN UP */}
                        <div className="md:col-span-2 flex justify-center mt-4">
                            <button type="submit">Sign Up</button>
                        </div>
                    </form>

                </div>

                <div className="row-span-1 flex justify-center">
                    <p>
                        Do you have an account?{" "}
                        <Link to={"/log-in"} className="link">
                            Log in →
                        </Link>
                    </p>
                </div>

            </div>
        </>
    );
}