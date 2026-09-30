import { useTheme } from "next-themes";
import { Toaster as Sonner, type ToasterProps } from "sonner";
import { CircleCheckIcon, InfoIcon, TriangleAlertIcon, OctagonXIcon, Loader2Icon } from "lucide-react";
import "./sonner.css";

const Toaster = ({ ...props }: ToasterProps) => {
    const { theme = "system" } = useTheme();

    return (
        <Sonner
            theme={theme as ToasterProps["theme"]}
            className="toaster group"
            icons={{
                success: <CircleCheckIcon className="toast-icon" />,
                info: <InfoIcon className="toast-icon" />,
                warning: <TriangleAlertIcon className="toast-icon" />,
                error: <OctagonXIcon className="toast-icon" />,
                loading: <Loader2Icon className="toast-icon animate-spin" />,
            }}
            style={{
                "--normal-bg": "#F7FBFF",
                "--normal-text": "#1E3A5F",
                "--normal-border": "#D5E1EE",
                "--border-radius": "1.25rem",
            } as React.CSSProperties}
            toastOptions={{
                classNames: {
                    toast: "cn-toast",
                    title: "cn-toast-title",
                    description: "cn-toast-description",
                    closeButton: "cn-toast-close",
                    success: "cn-toast-success",
                    error: "cn-toast-error",
                    warning: "cn-toast-warning",
                    info: "cn-toast-info",
                    loading: "cn-toast-loading",
                },
            }}
            {...props}
        />
    );
};

export { Toaster };