package com.example.weatherspringboot.repository;

import com.example.weatherspringboot.entity.LocationEntity;
import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;


public interface SavedDailyWeatherRepository extends JpaRepository<SavedDailyWeatherEntity, UUID> {
    Optional<SavedDailyWeatherEntity> findByLocationAndTime(LocationEntity location, LocalDate time);
}
