import { Dialog, DialogTrigger, DialogContent, DialogHeader, DialogTitle, DialogDescription, DialogFooter, DialogClose } from "../../../@/components/ui/dialog";

import { Button } from "../../../@/components/ui/button";
import { Input } from "../../../@/components/ui/input";
import { Label } from "../../../@/components/ui/label";
import { MessageCircleIcon, ShareIcon, Share2Icon, BirdIcon } from "lucide-react";

import { toast } from "sonner";
import "./ShareDailog.css";

export default function ShareDialog() {

    const message = `Check out this experience on MyErasmusJourney!\nDiscover all the details here:\n${window.location.href}\nReady to experience it yourself?`;


    function shareInWhatsApp() {

        window.open(`https://wa.me/?text=${encodeURIComponent(message)}`, "_blank", "noopener,noreferrer")

    }

    async function shareExperience() {
        const shareData = {
            title: "MyErasmusJourney",
            text: "Check out this experience on MyErasmusJourney!",
            url: window.location.href,
        };

        if (navigator.share) {
            try {
                await navigator.share(shareData);
            } catch (error) {
                console.error(error)
            }
        } else {
            await navigator.clipboard.writeText(shareData.url);
            toast.info("Unable to share experience, the link has been copied into the clipboard");
        }
    }

    return (<>
        <Dialog>
            <DialogTrigger render={
                <button type="button" aria-label="Share experience" className="w-11 h-11 flex items-center justify-center rounded-full p-2">
                    <Share2Icon />
                </button>
            } />
            <DialogContent className="container share-dialog-content">
                <DialogHeader className="share-dialog-header">
                    <DialogTitle className="share-dialog-title">Share experience</DialogTitle>
                    <DialogDescription className="share-dialog-description">
                        Share this experience with your friends and inspire other Erasmus students.
                    </DialogDescription>
                </DialogHeader>

                <div className="share-dialog-link">
                    <Label htmlFor="share-link" className="sr-only">Experience link</Label>
                    <Input id="share-link" className="share-dialog-input" value={window.location.href} readOnly />
                    <button type="button" className="share-copy-button" onClick={() => navigator.clipboard.writeText(window.location.href)}>
                        Copy link
                    </button>
                </div>

                <div className="share-social-buttons">
                    <button type="button" className="share-social-button share-whatsapp" onClick={shareInWhatsApp}>
                        <MessageCircleIcon />
                        <span>WhatsApp</span>
                    </button>
                    <button type="button" className="share-social-button share-x" onClick={() => window.open(`https://twitter.com/intent/tweet?url=${encodeURIComponent(message)}`, "_blank", "noopener,noreferrer")}>
                        <BirdIcon />
                        <span>X/Twitter</span>
                    </button>
                    <button type="button" className="share-social-button share-instagram" onClick={shareExperience}>
                        <ShareIcon />
                        <span>Other</span>
                    </button>
                </div>

                <DialogFooter className="share-dialog-footer">
                    <DialogClose render={<Button type="button" className="share-close-button">Close</Button>} />
                </DialogFooter>
            </DialogContent>
        </Dialog>
    </>)
}