package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.ExperienceMultimedia;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.domain.UserImage;
import com.myerasmusjourney.backend.dto.ExperienceMultimediaSimpleDTO;
import com.myerasmusjourney.backend.dto.UserImageDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.mapper.MultimediaMapper;
import com.myerasmusjourney.backend.mapper.UserMapper;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Tag;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class MultimediaMapperTest {

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks 
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

    @Test
    void TestToUserImageDTO(){
        try {
            User user = new User();
            MockMultipartFile file = new MockMultipartFile(
                    "file",
                    "image.jpg",
                    "image/jpeg",
                    "fake image content".getBytes()
            );

            UserImage userImage = new UserImage(file, user);

            UserImageDTO expected = new UserImageDTO(null, ".jpg", new UserSimpleDTO(null, null, null));
            UserImageDTO result = mapper.toDTO(userImage);

            assertEquals(expected, result);

        } catch (IOException e) {
            assertFalse(true);
        }
    }
}
