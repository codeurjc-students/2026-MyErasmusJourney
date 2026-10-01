import type { userServiceProps } from "@shared/interfaces/userServiceProps";
import type { authServiceProps } from "@shared/interfaces/authServiceProps";
import type { UserDTO } from "@shared/models/UserDTO";
import { createUserService } from "@shared/services/user.service";
import { API } from "../../api/client";
import { useEffect, useState } from "react";
import { useUserStore } from "@shared/stores/userStore";
import { useNavigate } from "react-router-dom";
import "./UserPage.css";
import { createAuthService } from "@shared/services/auth.service";
import UserExperiences from "../../components/UserExperiences/UserExperiences";
import UserComments from "../../components/UserComments/UserComments";
import { ApiError } from "@shared/api/apiError";
import { Trash2Icon } from "lucide-react"
import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogMedia,
    AlertDialogTitle,
    AlertDialogTrigger,
} from "../../../@/components/ui/alert-dialog"

export default function UserPage({ authService = createAuthService(API), userService = createUserService(API) }: authServiceProps & userServiceProps) {

    const [userDTO, setUserDTO] = useState<UserDTO | null>(null);

    const { user, setUser } = useUserStore();

    const navigate = useNavigate();

    function notAvailable() {
        navigate("/available-soon");
    }

    function updateUser() {
        navigate("/user/update");
    }

    function addCity() {
        navigate("/cities/new");
    }

    function addExperience() {
        navigate("/experiences/new");
    }

    useEffect(() => {

        const fetchUser = async () => {
            if (user != null) {
                try {
                    {
                        const data = await userService.getUserById(user.id);
                        setUserDTO(data);
                    }
                }
                catch (error) {
                    if (error instanceof ApiError && error.status >= 500) {
                        console.error(error);
                        navigate("/error");
                        return;
                    }
                    console.error(error)
                    navigate("/log-in")
                }
            }
            else {
                navigate("/log-in")
            }
        }
        fetchUser();
    }, [])

    async function logOut() {
        authService.logOut();
        setUser(null);
        navigate("/");
    }

    async function deleteAccount() {
        if (user?.id) {
            try {
                await userService.deleteUserById(user.id);
                await logOut();
            }
            catch (error) {
                if (error instanceof ApiError && error.status >= 500) {
                    console.log("testing delete internal error")
                    console.error(error);
                    navigate("/error");
                    return;
                }
                console.error(error)
            }
        }
    }

    return (<>
        <div className="container relative mx-auto max-w-6xl rounded-3xl bg-white shadow-2xl p-10">
            <h3 className="text-center mb-10" id="profileTitle">Profile</h3>
            <button onClick={logOut} className="button absolute top-8 right-8">Log out</button>
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-12">

                <div className="lg:col-span-2 flex flex-col justify-end">

                    <div className="grid md:grid-cols-2 gap-8">

                        <div className="space-y-4">
                            <p><span className="font-bold">Displayed Name:</span> {userDTO?.displayName}</p>
                            <p><span className="font-bold">Full Name:</span> {userDTO?.fullName}</p>
                        </div>

                        <div className="space-y-4">
                            <p><span className="font-bold">Email:</span> {userDTO?.email}</p>
                            {(userDTO?.studyLocation !== null && userDTO?.studyLocation !== "")
                                ? (
                                    <p><span className="font-bold">Studying in:</span> {userDTO?.studyLocation}</p>
                                ) : (
                                    <p><span className="font-bold">Studying in:</span> User has yet to complete this field</p>
                                )
                            }
                        </div>

                    </div>

                    <div className="flex justify-center gap-8 mt-10">
                        {(userDTO?.roles.includes("ADMIN"))
                            ? (
                                <button className="button" onClick={addCity}>Add City</button>
                            )
                            : null
                        }
                        <button className="button" onClick={updateUser}>Edit Profile</button>

                        <AlertDialog>
                            <AlertDialogTrigger render={<button className="button">Delete Profile</button>} />

                            <AlertDialogContent size="sm" className="container alert-dialog-content">
                                <AlertDialogHeader className="alert-dialog-header">
                                    <AlertDialogMedia className="alert-dialog-media bg-destructive/10 text-destructive dark:bg-destructive/20 dark:text-destructive">
                                        <Trash2Icon />
                                    </AlertDialogMedia>

                                    <div className="alert-dialog-body">
                                        <AlertDialogTitle className="alert-dialog-title">Delete your account?</AlertDialogTitle>

                                        <AlertDialogDescription className="alert-dialog-description">
                                            This action cannot be undone. Your account and associated data will be permanently deleted.
                                        </AlertDialogDescription>
                                    </div>
                                </AlertDialogHeader>

                                <AlertDialogFooter className="alert-dialog-footer">
                                    <div className="alert-dialog-actions">
                                        <AlertDialogCancel variant="outline">Cancel</AlertDialogCancel>
                                        <AlertDialogAction variant="destructive" onClick={deleteAccount}>Confirm</AlertDialogAction>
                                    </div>
                                </AlertDialogFooter>
                            </AlertDialogContent>
                        </AlertDialog>

                        <button className="button" onClick={addExperience}>New Experience</button>
                    </div>

                </div>

                <div className="flex flex-col justify-center items-center gap-8">
                    <img src="/images/available_soon.png" alt="profile image" className="mainProfileImage rounded-full object-cover" />
                    <button className="button" onClick={notAvailable}>Change Picture</button>
                </div>

            </div>
            <div className="mt-16 grid md:grid-cols-2 gap-10">
                <UserExperiences userService={userService} userExperiences={userDTO?.experiences} userId={userDTO?.id} />
                <UserComments userService={userService} userComments={userDTO?.comments} userId={userDTO?.id} />
            </div>

        </div>
    </>)
}