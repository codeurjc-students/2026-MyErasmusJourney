package com.myerasmusjourney.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
public class ExperienceMultimedia extends Multimedia {
    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    public ExperienceMultimedia(MultipartFile rawImage, Experience experience) throws IOException {
        this.experience = experience;
        this.setContentType(rawImage.getContentType());
        this.setMultimediaFile(rawImage);
    }

    public ExperienceMultimedia() {
    }

    public Experience getExperience() {
        return this.experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }
}
