package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.ExperienceMultimedia;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.ExperienceMultimediaSimpleDTO;
import com.myerasmusjourney.backend.mapper.MultimediaMapper;
import com.myerasmusjourney.backend.repository.ExperienceMultimediaRepository;
import com.myerasmusjourney.backend.service.ExperienceService;
import com.myerasmusjourney.backend.service.MultimediaService;
import com.myerasmusjourney.backend.service.UserService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class MultimediaServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ExperienceService experienceService;

    @Mock
    private ExperienceMultimediaRepository experienceMultimediaRepository;

    @Mock
    private MultimediaMapper multimediaMapper;

    @InjectMocks
    private MultimediaService multimediaService;

    @Test
    void testAddMultimedia(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");
        user.setRoles(List.of("User"));

        Experience experience = new Experience();
        experience.setAuthor(user);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "file",
                "image.png",
                "image/png",
                "fake image content".getBytes()
        );

        List<ExperienceMultimediaSimpleDTO> expected = List.of(
                new ExperienceMultimediaSimpleDTO(1L, ".jpg"),
                new ExperienceMultimediaSimpleDTO(2L, ".png")
        );

        try {
            ExperienceMultimedia experienceMultimedia = new ExperienceMultimedia(file, experience);
            experienceMultimedia.setId(1L);

            ExperienceMultimedia experienceMultimedia2 = new ExperienceMultimedia(file2, experience);
            experienceMultimedia2.setId(2L);

            when(experienceService.getExperience(1L)).thenReturn(experience);
            when(userService.getLoggedUser()).thenReturn(user);
            when(experienceMultimediaRepository.save(any(ExperienceMultimedia.class))).thenReturn(experienceMultimedia, experienceMultimedia2);
            when(multimediaMapper.toSimpleDTOs(argThat(multimedia -> multimedia.size() == 2))).thenReturn(expected);

            List<ExperienceMultimediaSimpleDTO> result = multimediaService.addMultimedia(1L, List.of(file, file2));

            assertEquals(expected, result);

            verify(experienceService).getExperience(1L);
            verify(userService).getLoggedUser();
            verify(experienceMultimediaRepository, times(2)).save(any(ExperienceMultimedia.class));
            verify(multimediaMapper).toSimpleDTOs(argThat(multimedia -> multimedia.size() == 2));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddMultimediaWithoutAuthentication(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");
        user.setRoles(List.of("User"));

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("user@email.com");
        user2.setRoles(List.of("User"));

        Experience experience = new Experience();
        experience.setAuthor(user);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "file",
                "image.png",
                "image/png",
                "fake image content".getBytes()
        );

        try {
            ExperienceMultimedia experienceMultimedia = new ExperienceMultimedia(file, experience);
            experienceMultimedia.setId(1L);

            ExperienceMultimedia experienceMultimedia2 = new ExperienceMultimedia(file2, experience);
            experienceMultimedia2.setId(2L);

            when(experienceService.getExperience(1L)).thenReturn(experience);
            when(userService.getLoggedUser()).thenReturn(user2);

            List<ExperienceMultimediaSimpleDTO> result = multimediaService.addMultimedia(1L, List.of(file, file2));

            assertNull(result);

            verify(experienceService).getExperience(1L);
            verify(userService).getLoggedUser();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddMultimediaWithNoValidContentType(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");
        user.setRoles(List.of("User"));

        Experience experience = new Experience();
        experience.setAuthor(user);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/whatever",
                "fake image content".getBytes()
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "file",
                "image.png",
                "image/png",
                "fake image content".getBytes()
        );

        try {
            ExperienceMultimedia experienceMultimedia = new ExperienceMultimedia(file, experience);
            experienceMultimedia.setId(1L);

            ExperienceMultimedia experienceMultimedia2 = new ExperienceMultimedia(file2, experience);
            experienceMultimedia2.setId(2L);

            when(experienceService.getExperience(1L)).thenReturn(experience);
            when(userService.getLoggedUser()).thenReturn(user);


            List<ExperienceMultimediaSimpleDTO> result = multimediaService.addMultimedia(1L, List.of(file, file2));

            assertTrue(result.isEmpty());

            verify(experienceService).getExperience(1L);
            verify(userService).getLoggedUser();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
