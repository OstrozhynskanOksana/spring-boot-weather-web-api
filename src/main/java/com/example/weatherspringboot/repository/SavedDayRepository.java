package com.example.weatherspringboot.repository;

import com.example.weatherspringboot.entity.LocationEntity;
import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;
import com.example.weatherspringboot.entity.SavedDayEntity;
import com.example.weatherspringboot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SavedDayRepository extends JpaRepository<SavedDayEntity, UUID> {
    Optional<SavedDayEntity> findByUserAndWeatherDay(UserEntity user, SavedDailyWeatherEntity weatherDay);

    boolean existsByUserAndWeatherDayLocationAndWeatherDayTime(
            UserEntity user,
            LocationEntity location,
            LocalDate time
    );

    @Query("""
            select distinct savedDay
            from SavedDayEntity savedDay
            left join fetch savedDay.eventEntity events
            join fetch savedDay.weatherDay weatherDay
            left join fetch weatherDay.location
            where savedDay.user.email = :email
            order by weatherDay.time asc
            """)
    List<SavedDayEntity> findAllWithEventsByUserEmail(@Param("email") String email);
}
