package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.UserImageDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

@Tag("unit")
public class UserImageDTOTest {

    @Test
    void testUserImageDTO(){
        UserImageDTO userImageDTO = new UserImageDTO(1L, ".png", new UserSimpleDTO(null, null, null));
        Long id = 1L;

        assertEquals(id, userImageDTO.id());
        assertEquals(".png", userImageDTO.contentType());
        assertEquals(new UserSimpleDTO(null, null, null), userImageDTO.userSimpleDTO());
    }
}
