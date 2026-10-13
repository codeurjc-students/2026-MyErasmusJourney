package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.ExperienceMultimedia;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.domain.UserImage;
import com.myerasmusjourney.backend.dto.ExperienceMultimediaSimpleDTO;
import com.myerasmusjourney.backend.dto.UserImageDTO;
import com.myerasmusjourney.backend.mapper.MultimediaMapper;
import com.myerasmusjourney.backend.repository.ExperienceMultimediaRepository;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;

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

            List<ExperienceMultimediaSimpleDTO> expected = multimediaMapper.toSimpleDTOs(List.of(experienceMultimedia1, experienceMultimedia2));

            List<ExperienceMultimediaSimpleDTO> result = multimediaService.addMultimedia(1L, List.of(file, file2));

            int i = 0;
            for (ExperienceMultimediaSimpleDTO dto: result){
                ExperienceMultimediaSimpleDTO expectedDTO = expected.get(i);
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

        List<ExperienceMultimediaSimpleDTO> result = multimediaService.addMultimedia(1L, List.of(file, file2));

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

        List<ExperienceMultimediaSimpleDTO> result = multimediaService.addMultimedia(1L, List.of(file, file2));

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
            UserImage userImage = new UserImage(file, user);

            UserImageDTO expected = multimediaMapper.toDTO(userImage);

            UserImageDTO result = multimediaService.addUserImage(user.getId(), file);

            assertEquals(expected.userSimpleDTO(), result.userSimpleDTO());
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
            UserImage userImage = new UserImage(file, user);

            UserImage previous = new UserImage(file2,user);

            previous = userImageRepository.save(previous);

            user.setProfileImage(previous);

            userRepository.save(user);

            UserImageDTO expected = multimediaMapper.toDTO(userImage);

            UserImageDTO result = multimediaService.addUserImage(user.getId(), file);

            assertEquals(expected.userSimpleDTO(), result.userSimpleDTO());
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
        assertNull(result.userSimpleDTO());
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
        assertNull(result.userSimpleDTO());
    }
}
