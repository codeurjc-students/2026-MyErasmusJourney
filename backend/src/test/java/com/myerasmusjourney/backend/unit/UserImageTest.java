package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.domain.UserImage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("unit")
public class UserImageTest {

    @Test
    void testConstructor(){
        User user = new User();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        try {
            UserImage userImage = new UserImage(file, user);
            assertEquals(".jpg", userImage.getContentType());
            assertEquals(user, userImage.getUser());
            assertNull(userImage.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSetters(){
        User user = new User();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        Long id = 1L;

        try {
            UserImage userImage = new UserImage();
            userImage.setUser(user);
            userImage.setId(id);
            userImage.setMultimediaFile(file);
            userImage.setContentType(file.getContentType());

            assertEquals(".jpg", userImage.getContentType());
            assertEquals(user, userImage.getUser());
            assertEquals(id, userImage.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
