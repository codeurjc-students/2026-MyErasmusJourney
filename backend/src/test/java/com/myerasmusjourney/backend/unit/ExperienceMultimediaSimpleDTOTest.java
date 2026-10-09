package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.ExperienceMultimediaSimpleDTO;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ExperienceMultimediaSimpleDTOTest {

    @Test
    void testExperienceMultimediaSimpleDTO(){
        ExperienceMultimediaSimpleDTO experienceMultimediaSimpleDTO = new ExperienceMultimediaSimpleDTO(1L, "jpg");
        Long id = 1L;
        assertEquals(id, experienceMultimediaSimpleDTO.id());
        assertEquals("jpg", experienceMultimediaSimpleDTO.contentType());
    }
}
