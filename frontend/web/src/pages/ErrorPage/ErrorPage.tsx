import { useNavigate } from "react-router-dom";

export default function ErrorPage() {

    const navigate = useNavigate();

    function redirectToHome() {
        navigate("/");
    }

    return (
        <>
            <div className="container mx-auto flex flex-col items-center gap-8 p-6 text-center">

                <img src="/images/error.png" alt="Error Image" className="mainImage max-w-sm w-3/4 h-auto"/>

                <button onClick={redirectToHome}>Back to home page</button>

            </div>
        </>
    );
}