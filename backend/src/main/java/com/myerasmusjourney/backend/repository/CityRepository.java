package com.myerasmusjourney.backend.repository;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByName(String name);

    @Query("""
        SELECT e
        FROM City c
        JOIN c.experiences e
        WHERE LOWER(c.name) = LOWER(:cityName)
          AND LOWER(c.country) = LOWER(:country)
          AND e.date >= :from
          AND e.date <= :to
    """)
    List<Experience> findRecentExperiencesOfCity(@Param("cityName") String cityName, @Param("country") String country, @Param("from")LocalDate from, @Param("to") LocalDate to);

    @Query("""
    SELECT AVG(e.rating)
    FROM Experience e
    WHERE e.city.id = :cityId
""")
    Double findAverageRatingByCityId(@Param("cityId") Long cityId);
}
