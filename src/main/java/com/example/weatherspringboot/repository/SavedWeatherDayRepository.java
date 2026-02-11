package com.example.weatherspringboot.repository;

import com.example.weatherspringboot.entity.LocationEntity;
import com.example.weatherspringboot.entity.SavedWeatherDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface SavedWeatherDayRepository extends JpaRepository<SavedWeatherDay, UUID> {
    boolean existsSavedWeatherDayBy(LocationEntity location, LocalDate date);
}
