import { Link } from "react-router-dom";
import "./Header.css";

export default function Header(){
    return (<>
        <header className="flex items-center justify-between">
            <Link to="/">
                <img src="/images/logo.png" alt="Logo" />
            </Link>

            <nav className="flex gap-8 items-center">
                <Link to="/">Home</Link>
                <Link to="/">About Us</Link>
                <Link to="/">Cities</Link>
                <Link to="/experiences">Experiences</Link>
                <Link to="/">Log in</Link>
                <Link to="/">Sign up</Link>
            </nav>
        </header>
    </>)
}