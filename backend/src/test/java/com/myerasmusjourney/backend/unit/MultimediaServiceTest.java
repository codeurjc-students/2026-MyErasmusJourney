package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.ExperienceMultimedia;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.domain.UserImage;
import com.myerasmusjourney.backend.dto.ExperienceMultimediaSimpleDTO;
import com.myerasmusjourney.backend.dto.UserImageDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.mapper.MultimediaMapper;
import com.myerasmusjourney.backend.repository.ExperienceMultimediaRepository;
import com.myerasmusjourney.backend.repository.UserImageRepository;
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
    private UserImageRepository userImageRepository;

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

    @Test
    void testAddProfileImage(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");
        user.setRoles(List.of("User"));

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        try {
            UserImage userImage = new UserImage(file, user);
            userImage.setId(1L);
            UserImageDTO expected = new UserImageDTO(1L, userImage.getContentType(), new UserSimpleDTO(1L, null, user.getEmail()));

            when(userService.getLoggedUser()).thenReturn(user);
            when(userImageRepository.save(any(UserImage.class))).thenReturn(userImage);
            when(multimediaMapper.toDTO(userImage)).thenReturn(expected);

            UserImageDTO result = multimediaService.addUserImage(1L, file);

            assertEquals(expected, result);

            verify(userService).getLoggedUser();
            verify(userImageRepository).save(any(UserImage.class));
            verify(multimediaMapper).toDTO(userImage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddProfileImageDeletingPrevious(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");
        user.setRoles(List.of("User"));

        UserImage savedUserImage = new UserImage();
        user.setProfileImage(savedUserImage);


        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        try {
            UserImage userImage = new UserImage(file, user);
            userImage.setId(1L);
            UserImageDTO expected = new UserImageDTO(1L, userImage.getContentType(), new UserSimpleDTO(1L, null, user.getEmail()));

            when(userService.getLoggedUser()).thenReturn(user);
            when(userImageRepository.save(any(UserImage.class))).thenReturn(userImage);
            when(multimediaMapper.toDTO(userImage)).thenReturn(expected);

            UserImageDTO result = multimediaService.addUserImage(1L, file);

            assertEquals(expected, result);

            verify(userService).getLoggedUser();
            verify(userImageRepository).save(any(UserImage.class));
            verify(multimediaMapper).toDTO(userImage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddProfileImageOfAnotherUser(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");
        user.setRoles(List.of("User"));

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/whatever",
                "fake image content".getBytes()
        );

        try {
            UserImage userImage = new UserImage(file, user);
            userImage.setId(1L);

            when(userService.getLoggedUser()).thenReturn(user);

            UserImageDTO result = multimediaService.addUserImage(2L, file);

            Long id = -1L;

            assertEquals(id, result.id());
            assertNull(result.contentType());
            assertNull(result.userSimpleDTO());

            verify(userService).getLoggedUser();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddProfileImageWithWrongContentType(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");
        user.setRoles(List.of("User"));

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.txt",
                "plain/text",
                "fake image content".getBytes()
        );

        try {
            UserImage userImage = new UserImage(file, user);
            userImage.setId(1L);

            when(userService.getLoggedUser()).thenReturn(user);

            UserImageDTO result = multimediaService.addUserImage(1L, file);

            Long id = 0L;

            assertEquals(id, result.id());
            assertNull(result.contentType());
            assertNull(result.userSimpleDTO());

            verify(userService).getLoggedUser();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
