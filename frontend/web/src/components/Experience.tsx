import type { ExperienceSimpleDTO } from "@shared/models/ExperienceSimpleDTO.ts";

interface HeaderProps {experience:ExperienceSimpleDTO;}


export default function Experience({experience}:HeaderProps){

    return(<>
        <div id={`experience-${experience.id}`}>
            <h4>{experience.title}</h4>
            <p>{experience.date}</p>
            <p>{experience.rating}</p>
            <p>{experience.description}</p>
        </div>
    </>)

}