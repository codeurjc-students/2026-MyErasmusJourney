package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.dto.CityDTO;
import com.myerasmusjourney.backend.mapper.CityMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
public class CityMapperTest {

    private final CityMapper cityMapper = Mappers.getMapper(CityMapper.class);

    @Test
    void testToDTO() {
        City city = new City("Madrid", "Spain", "Capital of Spain");
        city.setId(1L);

        CityDTO result = cityMapper.toDTO(city);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Madrid", result.name());
        assertEquals("Capital of Spain", result.description());
        assertEquals("Spain", result.country());
    }

    @Test
    void testToDTONull() {
        CityDTO result = cityMapper.toDTO(null);

        assertNull(result);
    }
}
