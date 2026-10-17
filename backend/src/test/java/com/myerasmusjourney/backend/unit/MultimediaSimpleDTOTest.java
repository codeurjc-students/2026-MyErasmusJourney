package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.MultimediaSimpleDTO;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class MultimediaSimpleDTOTest {

    @Test
    void testExperienceMultimediaSimpleDTO(){
        MultimediaSimpleDTO multimediaSimpleDTO = new MultimediaSimpleDTO(1L, "jpg");
        Long id = 1L;
        assertEquals(id, multimediaSimpleDTO.id());
        assertEquals("jpg", multimediaSimpleDTO.contentType());
    }
}
