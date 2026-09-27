package com.myerasmusjourney.backend.repository;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.enumeration.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("""
        SELECT e
        FROM Experience e
        WHERE (:cityName IS NULL OR LOWER(e.city.name) = LOWER(:cityName))
          AND (:minimumRate IS NULL OR e.rating >= :minimumRate)
          AND (:maximumRate IS NULL OR e.rating <= :maximumRate)
          AND (:from IS NULL OR e.date >= :from)
          AND (:to IS NULL OR e.date <= :to)
          AND (
              SELECT COUNT(DISTINCT c)
              FROM Experience e2
              JOIN e2.categories c
              WHERE e2 = e
                AND c IN :categories
          ) = :categoryCount
    """)
        Page<Experience> findFilteredWithCategories(
                @Param("cityName") String cityName,
                @Param("categories") List<Category> categories,
                @Param("categoryCount") long categoryCount,
                @Param("minimumRate") Float minimumRate,
                @Param("maximumRate") Float maximumRate,
                @Param("from") LocalDate from,
                @Param("to") LocalDate to,
                Pageable pageable
        );

    @Query("""
        SELECT e
        FROM Experience e
        WHERE (:cityName IS NULL OR LOWER(e.city.name) = LOWER(:cityName))
          AND (:minimumRate IS NULL OR e.rating >= :minimumRate)
          AND (:maximumRate IS NULL OR e.rating <= :maximumRate)
          AND (:from IS NULL OR e.date >= :from)
          AND (:to IS NULL OR e.date <= :to)
    """)
        Page<Experience> findFilteredWithoutCategories(
                @Param("cityName") String cityName,
                @Param("minimumRate") Float minimumRate,
                @Param("maximumRate") Float maximumRate,
                @Param("from") LocalDate from,
                @Param("to") LocalDate to,
                Pageable pageable
        );
}
