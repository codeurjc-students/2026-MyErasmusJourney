import { Dialog, DialogTrigger, DialogContent, DialogHeader, DialogTitle, DialogDescription, DialogFooter, DialogClose } from "../../../@/components/ui/dialog";

import { Button } from "../../../@/components/ui/button";
import { Input } from "../../../@/components/ui/input";
import { Label } from "../../../@/components/ui/label";

import "./ProfileImageDialog.css";
import type { userServiceProps } from "@shared/interfaces/userServiceProps";
import { API } from "@/api/client";
import { createUserService } from "@shared/services/user.service";
import { toast } from "sonner";
import { ApiError } from "@shared/api/apiError";
import { useNavigate } from "react-router-dom";

interface ProfileImageProps {
    id: number;
    setImageUpdated: (newValue: boolean) => void
}

export default function ProfileImageDialog({ userService = createUserService(API), id, setImageUpdated }: userServiceProps & ProfileImageProps) {


    const navigate = useNavigate();

    async function uploadUserImage() {
        const input = document.getElementById("imageFile") as HTMLInputElement;
        const file = input.files?.[0] ?? new File([], "");

        try {
            console.log(id);
            await userService.updateUserImage(id, file);
            toast.success("Profile Image updated");
            setImageUpdated(true);
        }
        catch (error) {
            console.log(error)
            if (error instanceof ApiError && error.status >= 500) {
                console.error(error);
                navigate("/error");
                return;
            }
            else if (error instanceof ApiError && error.status === 415) {
                toast.error("Unsupported file format. Please upload images (JPEG, PNG)");
            }
            else if (error instanceof Error) {
                toast.error(error.message);
            } else {
                toast.error("Error while uploading the image.");
            }
        }
    }


        return (<>
            <Dialog>
                <DialogTrigger render={
                    <button aria-label="Change picture" className="button">Change Picture</button>
                } />
                <DialogContent className="container share-dialog-content">
                    <DialogHeader className="share-dialog-header">
                        <DialogTitle className="share-dialog-title">Change your profile image</DialogTitle>
                        <DialogDescription className="share-dialog-description">
                            Upload a jpg or png file to use it as your profile image.
                        </DialogDescription>
                    </DialogHeader>

                    <form className="share-dialog-link">
                        <Label htmlFor="imageFile" className="sr-only"></Label>
                        <Input id="imageFile" className="share-dialog-input" type="file" accept="image/*" />
                    </form>

                    <DialogFooter className="share-dialog-footer">
                        <DialogClose render={<Button type="button" className="share-close-button" onClick={uploadUserImage}>Upload</Button>} />
                    </DialogFooter>
                </DialogContent>
            </Dialog>
        </>)
    }