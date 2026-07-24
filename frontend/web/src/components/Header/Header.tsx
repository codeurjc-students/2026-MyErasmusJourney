import { Link } from "react-router-dom";
import "./Header.css";
import { useUserStore } from "@shared/stores/userStore";
import { APIURL } from "../../config/env";

export default function Header(){

    const {user} = useUserStore();

    return (<>
        <header className="flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
            <Link to="/">
                <img src="/images/logo.png" alt="Logo" />
            </Link>

            <nav className="flex flex-wrap gap-4 items-center justify-center md:justify-end">
                <Link to="/">Home</Link>
                <Link to="/about-us">About Us</Link>
                <Link to="/">Cities</Link>
                <Link to="/experiences">Experiences</Link>
                {user !== null
                ?(
                    <div className="flex flex-wrap gap-1 items-center justify-center md:justify-end">
                        <img src={`https://${APIURL}/users/${user?.id}/image`} alt="img"/>
                        <Link to="/">{user.displayName}</Link>
                    </div>
                ):(
                    <div className="flex flex-wrap gap-4 items-center justify-center md:justify-end">
                        <Link to="/log-in">Log in</Link>
                        <Link to="/sign-up">Sign up</Link>
                    </div>
                )
                }
            </nav>
        </header>
    </>)
}