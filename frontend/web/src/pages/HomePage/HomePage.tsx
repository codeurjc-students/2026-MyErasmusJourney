import { Link } from "react-router-dom";
import "./HomePage.css";

export default function HomePage(){

    return(<>
        <div className="grid grid-cols-1 gap-5 lg:grid-cols-12"> 
            <div className="col-span-1 lg:col-span-8 mainInfo">
                <h2 data-testid="HomeTitle">Share your own <br/>Erasmus Experiences</h2>
                <p>
                    Every Erasmus journey tells a different story. 
                    Discover experiences shared by students from universities across Europe, 
                    explore destinations through first-hand accounts and share the moments 
                    that made your exchange unforgettable. <Link to={"/experiences"} className="link">Read the latest experiences →</Link></p>
            </div>
            <div className="col-span-1 lg:col-span-4">
                <img className="mainImage" src="/images/europe_map.png" alt="MAP OF EUROPE IN CIRCLE SHAPE"/>
            </div>
        </div>
        <div className="grid grid-cols-1 gap-5 md:grid-cols-9 functionalities">
            <div className="card col-span-1 md:col-span-3">
                <div className="grid grid-cols-3 gap-3 items-center">
                    <div className="col-span-1">
                        <img className="max-w-full h-auto" src="/images/compass.png" alt="opened book with a quill inside a compass"/>
                    </div>
                    <div className="col-span-2">
                        <h4>Write an <br/> adventure</h4>
                    </div>
                </div>
                <p className="mt-4">
                    Tell your Erasmus story and help future students discover what studying abroad is really like. 
                    Every experience can inspire someone else's journey.
                </p>
            </div>
            <div className="card col-span-1 md:col-span-3">
                <div className="grid grid-cols-3 gap-3 items-center">
                    <div className="col-span-1">
                        <img className="max-w-full h-auto" src="/images/globe.png" alt="opened book with a quill inside a compass"/>
                    </div>
                    <div className="col-span-2">
                        <h4>Explore destinations</h4>
                    </div>
                </div>
                <p className="mt-4">
                    Discover cities across Europe and learn about 
                    student life, accommodation, universities and local culture before choosing your next adventure.
                </p>
            </div>
            <div className="card col-span-1 md:col-span-3">
                <div className="grid grid-cols-3 gap-3 items-center">
                    <div className="col-span-1">
                        <img className="max-w-full h-auto" src="/images/book.png" alt="opened book with a quill inside a compass"/>
                    </div>
                    <div className="col-span-2">
                        <h4>Look at other journeys</h4>
                    </div>
                </div>
                <p className="mt-4">
                    Read authentic experiences written by Erasmus students, 
                    learn from their advice and prepare yourself to make the most of your own exchange.
                </p>
            </div>
        </div>
    </>)
}