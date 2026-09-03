import { useNavigate } from "react-router-dom";

export default function AvailableSoonPage() {

    const navigate = useNavigate();

    function redirectToHome() {
        navigate("/");
    }

    return (
        <>
            <div className="container mx-auto flex flex-col items-center gap-8 p-6 text-center">

                <img src="/images/available_soon.png" alt="Available Soon" className="mainImage max-w-sm w-3/4 h-auto"/>

                <p className="max-w-2xl">
                    It appears the page or action you want to reach is not available yet.
                    We encourage you to return to the home page so you can enjoy other
                    pages or actions.
                </p>

                <button onClick={redirectToHome}>🏠 Back to home page</button>

            </div>
        </>
    );
}