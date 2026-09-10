import { createUserService } from "@shared/services/user.service";
import type { userServiceProps } from "@shared/interfaces/userServiceProps";
import { API } from "../../api/client";
import { useEffect, useState, type FormEvent } from "react";
import "./UserFormPage.css";
import { Link, useNavigate } from "react-router-dom";
import { ApiError } from "@shared/api/apiError";
import { useUserStore } from "@shared/stores/userStore";
import type { UserDTO } from "@shared/models/UserDTO";

interface UserFormPageProps {
    mode: "signup" | "edit";
}

export default function UserFormPage({mode,userService = createUserService(API)}: UserFormPageProps & userServiceProps) {

    const navigate = useNavigate();
    const { user } = useUserStore();

    const [userDTO, setUserDTO] = useState<UserDTO | null>(null);
    const [loading, setLoading] = useState(mode === "edit");

    const isEdit = mode === "edit";

    useEffect(() => {

        if (!isEdit || user === null) {
            setLoading(false);
            return;
        }

        const fetchUserInfo = async () => {
            try {
                const fetchedUser = await userService.getUserById(user.id);
                setUserDTO(fetchedUser);
            }
            catch (error) {
                if (error instanceof ApiError && error.status >= 500) {
                    console.error(error);
                    navigate("/error");
                    return;
                }

                console.error(`Error fetching user info: ${error}`);
                alert(`Error fetching user info: ${error}`);
            }
            finally {
                setLoading(false);
            }
        };

        fetchUserInfo();

    }, [isEdit, user, userService, navigate]);


    async function handleEditSubmit(event: FormEvent<HTMLFormElement>) {

        event.preventDefault();

        if (userDTO === null) {
            return;
        }

        const form = event.currentTarget;
        const formData = new FormData(form);

        const fullName = formData.get("fullName") as string;
        const displayName = formData.get("displayName") as string;
        const email = formData.get("email") as string;
        const studyLocation =
            (formData.get("studyLocation") as string).trim();

        const changedUserDTO: UserDTO = {
            id: userDTO.id,
            email,
            displayName,
            fullName,
            studyLocation,
            experiences: userDTO.experiences,
            comments: userDTO.comments,
            roles: userDTO.roles
        };

        try {
            await userService.updateUser(
                changedUserDTO.id,
                changedUserDTO
            );

            navigate("/account");
        }
        catch (error) {
            if (error instanceof ApiError && error.status >= 500) {
                console.error(error);
                navigate("/error");
                return;
            }

            console.error(`Error updating user: ${error}`);
            alert(`Error updating user: ${error}`);
        }
    }


    async function handleSignUpSubmit(event: FormEvent<HTMLFormElement>) {

        event.preventDefault();

        const form = event.currentTarget;
        const formData = new FormData(form);

        const fullName = formData.get("fullName") as string;
        const displayName = formData.get("displayName") as string;
        const email = formData.get("email") as string;

        const city =
            (formData.get("city") as string).trim() || null;

        const country =
            (formData.get("country") as string).trim() || null;

        const password = formData.get("password") as string;
        const passwordConfirmation =
            formData.get("passwordConfirmation") as string;

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

            navigate("/log-in");
        }
        catch (error) {
            if (error instanceof ApiError && error.status >= 500) {
                console.error(error);
                navigate("/error");
                return;
            }

            console.error(`Error signing up: ${error}`);
            alert(`Error signing up: ${error}`);
        }
    }


    if (loading) {
        return (
            <div className="container mx-auto max-w-4xl p-6 text-center">
                <p>Loading...</p>
            </div>
        );
    }


    return (
        <div className="container mx-auto max-w-4xl p-6 grid gap-10 items-center">

            <div className="row-span-1 title">
                {isEdit ? (
                    <h3 id="editUserTitle">Edit Profile</h3>
                ) : (
                    <h3 id="signUpTitle">Sign Up</h3>
                )}
            </div>

            <div className="row-span-1">

                <form onSubmit={isEdit? handleEditSubmit: handleSignUpSubmit}className="grid grid-cols-1 md:grid-cols-2 gap-x-8 gap-y-4">

                    {/* FULL NAME */}
                    <div className="flex flex-col gap-2">
                        <label htmlFor="fullName">Full name</label>
                        <input type="text" id="fullName" name="fullName" defaultValue={userDTO?.fullName ?? ""} required/>
                    </div>


                    {/* IMAGE */}

                    <div className="md:row-span-5 flex justify-center items-center">
                        <img className="w-3/4 max-w-sm h-auto profileImg" src="/images/available_soon.png" alt="opened book with a quill inside a compass"/>
                    </div>


                    {/* PUBLIC NAME */}

                    <div className="flex flex-col gap-2">
                        <label htmlFor="displayName">Public name</label>
                        <input type="text" id="displayName" name="displayName" defaultValue={userDTO?.displayName ?? ""} required/>
                    </div>


                    {/* STUDY LOCATION / DESTINATION */}

                    {isEdit ? (
                        <div className="flex flex-col gap-2">
                            <label htmlFor="studyLocation">Study Location</label>
                            <input type="text" id="studyLocation" name="studyLocation" defaultValue={userDTO?.studyLocation ?? ""}/>
                        </div>

                    ) : (

                        <>
                            <div className="flex flex-col gap-2">
                                <label htmlFor="city">Destination City</label>
                                <input type="text"id="city"name="city"/>
                            </div>

                            <div className="flex flex-col gap-2">
                                <label htmlFor="country">Destination Country</label>
                                <input type="text" id="country"name="country"/>
                            </div>
                        </>

                    )}


                    {/* EMAIL */}

                    <div className="flex flex-col gap-2">
                        <label htmlFor="email">Email</label>
                        <input type="email" id="email" name="email" defaultValue={userDTO?.email ?? ""} required/>
                    </div>


                    {/* PASSWORD */}

                    {!isEdit && (
                        <>
                            <div className="flex flex-col gap-2">
                                <label htmlFor="password">Password</label>
                                <input type="password" id="password" name="password" required/>
                            </div>

                            <div className="flex flex-col gap-2">
                                <label htmlFor="passwordConfirmation">Repeat Password</label>
                                <input type="password" id="passwordConfirmation" name="passwordConfirmation"required/>
                            </div>
                        </>
                    )}

                    {/* SUBMIT */}
                    <div className="md:col-span-2 flex justify-center mt-4">
                        <button type="submit">{isEdit? "Save Changes" : "Sign Up"}</button>
                    </div>

                </form>

            </div>

            {/* LOGIN LINK */}
            {!isEdit && (
                <div className="row-span-1 flex justify-center">
                    <p>Do you have an account?{" "}<Link to="/log-in"className="link">Log in →</Link></p>
                </div>
            )}

        </div>
    );
}