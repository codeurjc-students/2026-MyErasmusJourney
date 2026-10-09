package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.ExperienceMultimedia;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.Assert.*;

public class ExperienceMultimediaTest {

    @Test
    void testEmptyConstructor(){
        ExperienceMultimedia experienceMultimedia = new ExperienceMultimedia();
        assertNull(experienceMultimedia.getId());
        assertNull(experienceMultimedia.getExperience());
        assertNull(experienceMultimedia.getContentType());
        assertNull(experienceMultimedia.getMultimediaFile());
    }

    @Test
    void testConstructor(){
        Experience experience = new Experience();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        try {
            ExperienceMultimedia experienceMultimedia = new ExperienceMultimedia( file, experience);

            assertEquals(".jpg", experienceMultimedia.getContentType());
            assertEquals(experience, experienceMultimedia.getExperience());
            assertNotNull(experienceMultimedia.getMultimediaFile());
        } catch (IOException e) {
            assertFalse(true);
        }
    }

    @Test
    void testSetters(){
        ExperienceMultimedia experienceMultimedia = new ExperienceMultimedia();
        assertNull(experienceMultimedia.getId());
        assertNull(experienceMultimedia.getExperience());
        assertNull(experienceMultimedia.getContentType());
        assertNull(experienceMultimedia.getMultimediaFile());

        Experience experience = new Experience();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );
        try {
            experienceMultimedia.setExperience(experience);
            experienceMultimedia.setMultimediaFile(file);
            experienceMultimedia.setId(1L);
            experienceMultimedia.setContentType(file.getContentType());

            assertEquals(".jpg", experienceMultimedia.getContentType());
            assertEquals(experience, experienceMultimedia.getExperience());
            assertNotNull(experienceMultimedia.getMultimediaFile());
        } catch (IOException e) {
            assertFalse(true);
        }
    }
}
