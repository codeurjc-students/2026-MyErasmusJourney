package com.myerasmusjourney.backend.repository;

import com.myerasmusjourney.backend.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByName(String name);
}
