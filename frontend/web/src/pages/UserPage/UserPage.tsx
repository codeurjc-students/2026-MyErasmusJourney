import type { userServiceProps } from "@shared/interfaces/userServiceProps";
import type {UserDTO} from "@shared/models/UserDTO";
import { createUserService } from "@shared/services/user.service";
import { API } from "../../api/client";
import { useEffect, useState } from "react";
import { useUserStore } from "@shared/stores/userStore";
import { useNavigate } from "react-router-dom";
import "./UserPage.css";

export default function UserPage({userService = createUserService(API)}: userServiceProps){

    const [userDTO, setUser] = useState<UserDTO|null>(null);

    const {user} = useUserStore();

    const navigate = useNavigate();

    useEffect(() => {
            
            const fetchUser = async () => {
                if(user != null){
                    try{
                        
                        {
                            const data = await userService.getUserById(user.id);
                            setUser(data);
                        }
                    }
                    catch(error){
                        console.error(error)
                        navigate("/log-in")
                    }
                }
                else{
                    navigate("/log-in")
                }
            }
            fetchUser();
        }, [])

    return(<>
        <div className="container mx-auto max-w-6xl rounded-3xl bg-white shadow-2xl p-10">
            <h3 className="text-center mb-10">Profile</h3>
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-12">

            <div className="lg:col-span-2 flex flex-col justify-end">

                <div className="grid md:grid-cols-2 gap-8">

                    <div className="space-y-4">
                        <p><span className="font-bold">Displayed Name:</span> {userDTO?.displayName}</p>
                        <p><span className="font-bold">Full Name:</span> {userDTO?.fullName}</p>
                    </div>

                    <div className="space-y-4">
                        <p><span className="font-bold">Email:</span> {userDTO?.email}</p>
                        <p><span className="font-bold">Studying in:</span> {userDTO?.studyLocation}</p>
                    </div>

                </div>

                <div className="flex justify-center gap-8 mt-10">
                    <button className="button">Edit Profile</button>
                    <button className="button">New Experience</button>
                </div>

            </div>

            <div className="flex flex-col justify-center items-center gap-8">
                <img src="/images/available_soon.png" alt="profile image" className="mainImage rounded-full object-cover"/>
                <button className="button">Change Picture</button>
            </div>

        </div>
            <div className="mt-16 grid md:grid-cols-2 gap-10">
                <div className="md:pr-8 md:border-r">
                    <h4 className="mb-6">Experiences</h4>
                </div>
                <div className="md:pl-8">
                    <h4 className="mb-6">Comments</h4>
                </div>
            </div>

        </div>
    </>)
}