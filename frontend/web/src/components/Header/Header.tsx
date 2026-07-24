import { Link } from "react-router-dom";
import "./Header.css";

export default function Header(){
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
                <Link to="/log-in">Log in</Link>
                <Link to="/sign-up">Sign up</Link>
            </nav>
        </header>
    </>)
}