package com.example.weatherspringboot.repository;

import com.example.weatherspringboot.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
}
