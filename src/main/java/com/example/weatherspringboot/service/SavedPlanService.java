package com.example.weatherspringboot.service;

import com.example.weatherspringboot.dto.SavedPlanDto;
import com.example.weatherspringboot.entity.EventEntity;
import com.example.weatherspringboot.entity.LocationEntity;
import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;
import com.example.weatherspringboot.entity.SavedDayEntity;
import com.example.weatherspringboot.entity.UserEntity;
import com.example.weatherspringboot.repository.SavedDailyWeatherRepository;
import com.example.weatherspringboot.repository.SavedDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SavedPlanService {
    private final SavedDayRepository savedDayRepository;
    private final SavedDailyWeatherRepository savedDailyWeatherRepository;
    private final LocationService locationService;
    private final UserService userService;
    private final NotificationService notificationService;

    @Transactional(readOnly = true)
    public List<SavedPlanDto> getPlans(String email) {
        return savedDayRepository.findAllWithEventsByUserEmail(email).stream()
                .flatMap(savedDay -> savedDay.getEventEntity().stream()
                        .map(event -> toDto(savedDay, event)))
                .sorted(Comparator
                        .comparing(SavedPlanDto::getDate)
                        .thenComparing(SavedPlanDto::getTime, Comparator.nullsLast(String::compareTo)))
                .toList();
    }

    @Transactional
    public SavedPlanDto addPlan(String email, SavedPlanDto request) {
        UserEntity user = userService.findByEmail(email);
        LocationEntity location = locationService.getOrCreateLocation(request.getCity());
        SavedDailyWeatherEntity weatherDay = savedDailyWeatherRepository
                .findByLocationAndTime(location, request.getDate())
                .orElseGet(() -> createWeatherDay(location, request));

        SavedDayEntity savedDay = savedDayRepository.findByUserAndWeatherDay(user, weatherDay)
                .orElseGet(() -> {
                    SavedDayEntity newSavedDay = new SavedDayEntity();
                    newSavedDay.setUser(user);
                    newSavedDay.setWeatherDay(weatherDay);
                    newSavedDay.setEventEntity(new ArrayList<>());
                    return newSavedDay;
                });

        EventEntity event = new EventEntity();
        event.setSavedDayEntity(savedDay);
        event.setTitle(request.getTitle());
        event.setTime(request.getTime());
        event.setDescription(request.getDescription());
        savedDay.getEventEntity().add(event);

        SavedDayEntity persisted = savedDayRepository.save(savedDay);
        notificationService.send(user, weatherDay);
        return toDto(persisted, event);
    }

    @Transactional
    public void deletePlan(String email, UUID eventId) {
        SavedDayEntity savedDay = savedDayRepository.findAllWithEventsByUserEmail(email).stream()
                .filter(day -> day.getEventEntity().stream().anyMatch(event -> event.getId().equals(eventId)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Plan is not found"));

        savedDay.getEventEntity().removeIf(event -> event.getId().equals(eventId));
        if (savedDay.getEventEntity().isEmpty()) {
            savedDayRepository.delete(savedDay);
        }
    }

    private SavedDailyWeatherEntity createWeatherDay(LocationEntity location, SavedPlanDto request) {
        SavedDailyWeatherEntity weatherDay = new SavedDailyWeatherEntity();
        weatherDay.setLocation(location);
        weatherDay.setTime(request.getDate());
        weatherDay.setWeatherCodes(request.getWeatherCode());
        weatherDay.setTempMax(request.getTempMax());
        weatherDay.setTempMin(request.getTempMin());
        weatherDay.setRainSum(request.getRainSum());
        return savedDailyWeatherRepository.save(weatherDay);
    }

    private SavedPlanDto toDto(SavedDayEntity savedDay, EventEntity event) {
        SavedDailyWeatherEntity weatherDay = savedDay.getWeatherDay();
        SavedPlanDto dto = new SavedPlanDto();
        dto.setId(event.getId());
        dto.setDate(weatherDay.getTime());
        dto.setTime(event.getTime());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setCity(weatherDay.getLocation().getCity());
        dto.setWeatherCode(weatherDay.getWeatherCodes());
        dto.setTempMax(weatherDay.getTempMax());
        dto.setTempMin(weatherDay.getTempMin());
        dto.setRainSum(weatherDay.getRainSum());
        return dto;
    }
}
