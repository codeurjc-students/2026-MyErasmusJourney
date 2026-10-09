package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.ExperienceMultimedia;
import com.myerasmusjourney.backend.dto.ExperienceMultimediaSimpleDTO;
import com.myerasmusjourney.backend.mapper.MultimediaMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class MultimediaMapperTest {

    private final MultimediaMapper mapper = Mappers.getMapper(MultimediaMapper.class);

    @Test
    void testToSimpleDTOs(){
        Experience experience = new Experience();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );
        MockMultipartFile file2 = new MockMultipartFile(
                "file",
                "image.png",
                "image/'png'",
                "fake image content".getBytes()
        );

        try {
            ExperienceMultimedia experienceMultimedia = new ExperienceMultimedia( file, experience);
            ExperienceMultimedia experienceMultimedia2 = new ExperienceMultimedia( file2, experience);

            List<ExperienceMultimediaSimpleDTO> experienceMultimediaSimpleDTOList = mapper.toSimpleDTOs(List.of(experienceMultimedia, experienceMultimedia2));

            assertEquals(2, experienceMultimediaSimpleDTOList.size());
            assertEquals(experienceMultimedia.getContentType(), experienceMultimediaSimpleDTOList.get(0).contentType());
            assertEquals(experienceMultimedia2.getContentType(), experienceMultimediaSimpleDTOList.get(1).contentType());
        } catch (IOException e) {
            assertFalse(true);
        }
    }
}
