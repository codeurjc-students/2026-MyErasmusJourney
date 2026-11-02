package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.LocationDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("unit")
public class LocationDTOTest {

    @Test
    void testLocationFormDTO() {
        LocationDTO locationDTO = new LocationDTO(-1.541, 4.416782 );
        assertEquals(-1.541, locationDTO.longitude());
        assertEquals(4.416782, locationDTO.latitude());
    }

    @Test
    void testLocationDTONullValues() {
        LocationDTO locationDTO = new LocationDTO(null, null);
        assertNull(locationDTO.longitude());
        assertNull(locationDTO.latitude());
    }

    @Test
    void testLocationDTOEquality() {
        LocationDTO locationDTO = new LocationDTO(-1.541, 4.416782 );
        LocationDTO locationDTO2 = new LocationDTO(-1.541, 4.416782 );

        assertEquals(locationDTO, locationDTO2);
        assertEquals(locationDTO.hashCode(), locationDTO2.hashCode());
    }
}
