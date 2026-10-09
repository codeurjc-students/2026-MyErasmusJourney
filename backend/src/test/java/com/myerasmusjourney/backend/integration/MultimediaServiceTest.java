package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.ExperienceMultimedia;
import com.myerasmusjourney.backend.dto.ExperienceMultimediaSimpleDTO;
import com.myerasmusjourney.backend.mapper.MultimediaMapper;
import com.myerasmusjourney.backend.repository.ExperienceMultimediaRepository;
import com.myerasmusjourney.backend.service.ExperienceService;
import com.myerasmusjourney.backend.service.MultimediaService;
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
    private MultimediaMapper multimediaMapper;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private MultimediaService multimediaService;


    @BeforeEach
    void clearAuthenticationContext(){
        SecurityContextHolder.clearContext();
        experienceMultimediaRepository.deleteAll();
    }

    @Test
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
}
