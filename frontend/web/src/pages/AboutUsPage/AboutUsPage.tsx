import "./AboutUsPage.css";

export default function AboutUsPage() {
    return(
        <>
            <div className="logo">
                <img src="/images/logo.png" alt="Logo"/>
            </div>
            <div className="grid-rows-3 grid gap-10 items-center info">
                <div className="row-span-1 container grid grid-cols-12 gap-3">
                    <div className="col-span-1">
                        <img src="/images/who_are_we.png" alt="opened book with a quill inside a compass"/>
                    </div>
                    <div className="col-span-11">
                        <h4>Who are we?</h4>
                        <p>
                                I am a Computer Science student at King Juan Carlos University in Madrid, Spain. 
                                I have always been interested in web development and creating applications that solve real-world problems.
                                Like thousands of other students, I had the opportunity to study abroad through the Erasmus+ programme,
                                 an experience that completely changed the way I see travelling, learning and connecting with people from different cultures.
                        </p>
                    </div>
                </div>
                <div className="row-span-1 container grid grid-cols-12 gap-3 ">
                    <div className="col-span-11 alternate-row">
                        <h4>What can you do in MyErasmusJourney?</h4>
                        <p>
                            MyErasmusJourney offers a wide range of features designed to help students make the most of their Erasmus experience.
                            Read authentic stories shared by students from different European countries, 
                            discover new destinations and learn from first-hand experiences before starting your own journey. 
                            Experiences cover a variety of topics, including accommodation, university life, gastronomy, social events,
                            Erasmus documentation and personal adventures.
                        </p>
                    </div>
                    <div className="col-span-1">
                        <img src="/images/what_can_you_do.png" alt=""/>
                    </div>
                </div>
                <div className="row-span-1 container grid grid-cols-12 gap-3">
                    <div className="col-span-1">
                        <img src="/images/why_develop_myerasmusjourney.png" alt="opened book with a quill inside a compass"/>
                    </div>
                    <div className="col-span-11">
                        <h4>Why develop MyErasmusJourney?</h4>
                        <p>
                            When I was preparing for my Erasmus experience, I found it difficult to find the information 
                            I needed about my destination. I wanted to read authentic stories from students who had already been there, 
                            but most of the information I found did not answer my questions. Instead, I ended up in a group chat with 
                            many other students, where it was difficult to keep track of conversations or get the answers I was looking for. 
                            After returning home, I came up with the idea of creating a platform where students could easily share and 
                            discover authentic Erasmus experiences.
                        </p>
                    </div>
                </div>
            </div>
        </>
    )
}