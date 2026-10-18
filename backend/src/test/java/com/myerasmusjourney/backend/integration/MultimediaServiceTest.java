package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.*;
import com.myerasmusjourney.backend.dto.MultimediaSimpleDTO;
import com.myerasmusjourney.backend.dto.UserImageDTO;
import com.myerasmusjourney.backend.mapper.MultimediaMapper;
import com.myerasmusjourney.backend.repository.ExperienceMultimediaRepository;
import com.myerasmusjourney.backend.repository.ExperienceRepository;
import com.myerasmusjourney.backend.repository.UserImageRepository;
import com.myerasmusjourney.backend.repository.UserRepository;
import com.myerasmusjourney.backend.service.ExperienceService;
import com.myerasmusjourney.backend.service.MultimediaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@SpringBootTest
@Tag("integration")
public class MultimediaServiceTest extends TestDataBase {

    @Autowired
    private ExperienceMultimediaRepository experienceMultimediaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private MultimediaMapper multimediaMapper;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private MultimediaService multimediaService;


    @BeforeEach
    void clearAuthenticationContext(){
        SecurityContextHolder.clearContext();
        experienceMultimediaRepository.deleteAll();
        userImageRepository.deleteAll();
    }

    @Test
    @Transactional
    void testAddMultimedia(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

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

        Experience experience = experienceService.getExperience(1L);

        try {
            ExperienceMultimedia experienceMultimedia1 = new ExperienceMultimedia(file, experience);
            ExperienceMultimedia experienceMultimedia2 = new ExperienceMultimedia(file2, experience);

            List<MultimediaSimpleDTO> expected = multimediaMapper.toSimpleDTOs(List.of(experienceMultimedia1, experienceMultimedia2));

            List<MultimediaSimpleDTO> result = multimediaService.addMultimedia(1L, List.of(file, file2));

            int i = 0;
            for (MultimediaSimpleDTO dto: result){
                MultimediaSimpleDTO expectedDTO = expected.get(i);
                assertNotNull(dto.id());
                assertEquals(expectedDTO.contentType(), dto.contentType());
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Transactional
    void testAddMultimediaWithWrongAuthentication(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser2@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

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

        List<MultimediaSimpleDTO> result = multimediaService.addMultimedia(1L, List.of(file, file2));

        assertNull(result);
    }

    @Test
    @Transactional
    void testAddMultimediaWithWrongContentType(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "file",
                "image.png",
                "image/whatever",
                "fake image content".getBytes()
        );

        List<MultimediaSimpleDTO> result = multimediaService.addMultimedia(1L, List.of(file, file2));

        assertTrue(result.isEmpty());
    }

    @Test
    @Transactional
    void testAddProfileImage(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        User user = userRepository.findByEmail("exampleuser1@email.com");
        try {
            UserImageDTO result = multimediaService.addUserImage(user.getId(), file);

            user = userRepository.findByEmail("exampleuser1@email.com");
            UserImage userImage = new UserImage(file, user);

            UserImageDTO expected = multimediaMapper.toDTO(userImage);


            assertEquals(expected.user(), result.user());
            assertEquals(expected.contentType(), result.contentType());
            assertNotNull(result.id());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Transactional
    void testAddProfileImageDeletingPrevious(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

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

        User user = userRepository.findByEmail("exampleuser1@email.com");
        try {
            UserImage previous = new UserImage(file2,user);

            previous = userImageRepository.save(previous);

            user.setProfileImage(previous);

            userRepository.save(user);

            UserImageDTO result = multimediaService.addUserImage(user.getId(), file);

            user = userRepository.findByEmail("exampleuser1@email.com");
            UserImage userImage = new UserImage(file, user);
            UserImageDTO expected = multimediaMapper.toDTO(userImage);

            assertEquals(expected.user(), result.user());
            assertEquals(expected.contentType(), result.contentType());
            assertNotNull(result.id());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Transactional
    void testAddProfileImageOfAnotherUser(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        User user = userRepository.findByEmail("exampleuser1@email.com");
        UserImageDTO result = multimediaService.addUserImage(user.getId(), file);

        Long id = -1L;

        assertEquals(id, result.id());
        assertNull(result.contentType());
        assertNull(result.user());
    }

    @Test
    @Transactional
    void testAddProfileImageWithWrongContentType(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.txt",
                "plain/text",
                "fake image content".getBytes()
        );

        User user = userRepository.findByEmail("exampleuser1@email.com");

        UserImageDTO result = multimediaService.addUserImage(user.getId(), file);

        Long id = 0L;

        assertEquals(id, result.id());
        assertNull(result.contentType());
        assertNull(result.user());
    }

    @Test
    void testLoadProfileImageAsResource(){
        User user = userRepository.findByEmail("test@email.com");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/whatever",
                "fake image content".getBytes()
        );

        try {
            UserImage userImage = new UserImage(file, user);
            userImage = userImageRepository.save(userImage);
            user.setProfileImage(userImage);
            userRepository.save(user);

            Resource expected = new ByteArrayResource(userImage.getMultimediaFile());
            Multimedia multimedia = multimediaService.getUserImageById(userImage.getId());
            Resource result = multimediaService.loadFileAsResource(multimedia.getMultimediaFile());
            assertEquals(expected, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void testGetUserProfile(){
        User user = userRepository.findByEmail("test@email.com");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/whatever",
                "fake image content".getBytes()
        );

        try {
            UserImage userImage = new UserImage(file, user);
            userImage = userImageRepository.save(userImage);
            user.setProfileImage(userImage);
            userRepository.save(user);

            Multimedia expected = userImage;
            Multimedia result = multimediaService.getUserImageById(userImage.getId());
            assertEquals(expected.getId(), result.getId());
            assertEquals(expected.getMultimediaFile().length, result.getMultimediaFile().length);
            assertEquals(expected.getContentType(), result.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetUserProfileNotFound(){
        assertThrows(NoSuchElementException.class, () -> multimediaService.getUserImageById(0L));
    }

    @Test
    @Transactional
    void testGetExperienceMultimedia(){
        Experience experience = experienceRepository.findById(1L).orElseThrow(()-> new NoSuchElementException("Not found"));

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/whatever",
                "fake image content".getBytes()
        );

        try {
            ExperienceMultimedia experienceMultimedia = new ExperienceMultimedia(file, experience);
            experienceMultimedia = experienceMultimediaRepository.save(experienceMultimedia);
            experience.addMultimedia(experienceMultimedia);
            experienceRepository.save(experience);

            Multimedia expected = experienceMultimedia;
            Multimedia result = multimediaService.getExperienceMultimediaById(experienceMultimedia.getId());
            assertEquals(expected.getId(), result.getId());
            assertEquals(expected.getMultimediaFile().length, result.getMultimediaFile().length);
            assertEquals(expected.getContentType(), result.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetExperienceMultimediaNotFound(){
        assertThrows(NoSuchElementException.class, () -> multimediaService.getExperienceMultimediaById(0L));
    }
}
