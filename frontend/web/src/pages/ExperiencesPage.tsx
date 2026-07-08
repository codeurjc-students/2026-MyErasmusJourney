import { useEffect, useState } from "react";

import Experience from "../components/Experience";
import {API} from "../api/client"

import type { ExperienceSimpleDTO } from "@shared/models/ExperienceSimpleDTO";
import {createExperienceService, type ExperienceService} from "@shared/services/experience.service"


interface ExperiencesPageProps {
    experienceService?: ExperienceService;
}

export default function ExperiencesPage({experienceService = createExperienceService(API)}: ExperiencesPageProps) {

    const [experiences, setExperiences] = useState<ExperienceSimpleDTO[]>([]);

    useEffect(() => {
        console.log("Inside Experiences...")
        const fetchData = async () => {
            try{
                const data = await experienceService.getAll();
                setExperiences(data)
            }
            catch(error){
                console.error(error)
            }
        }
        fetchData();
    }, [])

    return (<>
        <div id="experiences">
            <h3>Experiences</h3>
            {experiences.map(exp => <Experience key={exp.id} experience={exp}/>)}
        </div>
    </>)
}