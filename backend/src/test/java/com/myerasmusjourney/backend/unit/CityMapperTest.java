package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.dto.CityDTO;
import com.myerasmusjourney.backend.dto.CitySimpleDTO;
import com.myerasmusjourney.backend.mapper.CityMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

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

    @Test
    void testToSimpleDTOs() {
        City madrid = new City(
                "Madrid",
                "Spain",
                "Capital of Spain"
        );
        madrid.setId(1L);

        City rome = new City(
                "Rome",
                "Italy",
                "Capital of Italy"
        );
        rome.setId(2L);

        City berlin = new City(
                "Berlin",
                "Germany",
                "Capital of Germany"
        );
        berlin.setId(3L);

        List<City> cities = List.of(madrid, rome, berlin);

        Collection<CitySimpleDTO> result = cityMapper.toSimpleDTOs(cities);

        assertNotNull(result);
        assertEquals(3, result.size());

        long id = 1L;
        int i = 0;
        for(CitySimpleDTO dto: result){
            assertEquals(id, dto.id());
            assertEquals(cities.get(i).getName(), dto.name());
            assertEquals(cities.get(i).getCountry(), dto.country());
            id += 1;
            i ++;
        }
    }

    @Test
    void testToSimpleDTOsEmpty() {
        Collection<CitySimpleDTO> result = cityMapper.toSimpleDTOs(List.of());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
