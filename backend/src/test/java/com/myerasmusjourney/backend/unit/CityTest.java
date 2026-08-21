package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Experience;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
public class CityTest {

    @Test
    void testEmptyConstructor() {
        City city = new City();

        assertNull(city.getId());
        assertNull(city.getName());
        assertNull(city.getCountry());
        assertNull(city.getDescription());

        assertNotNull(city.getExperiences());
        assertTrue(city.getExperiences().isEmpty());
    }

    @Test
    void testConstructor() {
        City city = new City("Madrid", "Spain", "Capital of Spain");

        assertNull(city.getId());
        assertEquals("Madrid", city.getName());
        assertEquals("Spain", city.getCountry());
        assertEquals("Capital of Spain", city.getDescription());

        assertNotNull(city.getExperiences());
        assertTrue(city.getExperiences().isEmpty());
    }

    @Test
    void testSetAndGetId() {
        City city = new City();

        city.setId(1L);

        assertEquals(1L, city.getId());
    }

    @Test
    void testSetAndGetName() {
        City city = new City();

        city.setName("Berlin");

        assertEquals("Berlin", city.getName());
    }

    @Test
    void testSetAndGetCountry() {
        City city = new City();

        city.setCountry("Germany");

        assertEquals("Germany", city.getCountry());
    }

    @Test
    void testSetAndGetDescription() {
        City city = new City();

        city.setDescription("Capital of Germany");

        assertEquals("Capital of Germany", city.getDescription());
    }

    @Test
    void testSetAndGetExperiences() {
        City city = new City();

        List<Experience> experiences = new LinkedList<>();

        city.setExperiences(experiences);

        assertSame(experiences, city.getExperiences());
    }

    @Test
    void testSetExperiencesWithMultipleExperiences() {
        City city = new City();

        Experience experience1 = new Experience();
        Experience experience2 = new Experience();

        city.addExperience(experience1);
        city.addExperience(experience2);

        assertEquals(2, city.getExperiences().size());
        assertEquals(experience1, city.getExperiences().get(0));
        assertEquals(experience2, city.getExperiences().get(1));

        city.addExperience(experience1);

        assertEquals(3, city.getExperiences().size());
        assertEquals(experience1, city.getExperiences().get(2));
    }
}
