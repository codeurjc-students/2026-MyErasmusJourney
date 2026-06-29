import { useEffect, useState } from "react";

import Experience from "../components/Experience";
import {API} from "../api/client"

import type { ExperienceSimpleDTO } from "@shared/models/ExperienceSimpleDTO";
import {createExperienceService} from "@shared/services/experience.service"


export default function ExperiencesPage() {

    const [experiences, setExperiences] = useState<ExperienceSimpleDTO[]>([]);

    const experienceService = createExperienceService(API);

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
        <div>
            <h3>Experiences</h3>
            {experiences.map(exp => <Experience key={exp.id} experience={exp}/>)}
        </div>
    </>)
}