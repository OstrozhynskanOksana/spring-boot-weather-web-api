package com.example.weatherspringboot.service;

import com.example.weatherspringboot.config.ApiClient;
import com.example.weatherspringboot.dto.DailyWeatherDto;
import com.example.weatherspringboot.dto.LocationDto;
import com.example.weatherspringboot.dto.WeatherResponseDto;
import com.example.weatherspringboot.entity.LocationEntity;
import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;
import com.example.weatherspringboot.repository.LocationRepository;
import com.example.weatherspringboot.repository.SavedDailyWeatherRepository;
import com.example.weatherspringboot.service.Observer.WeatherStation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private SavedDailyWeatherRepository savedDailyWeatherRepository;

    @Mock
    private WeatherStation weatherStation;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void getWeatherResponseByCity_shouldReturnWeatherResponse() {
        LocationDto locationDto = new LocationDto();
        locationDto.setLatitude(50.45);
        locationDto.setLongitude(30.52);

        WeatherResponseDto responseDto = new WeatherResponseDto();
        responseDto.setLatitude(50.45);
        responseDto.setLongitude(30.52);
        responseDto.setTimezone("Europe/Kyiv");

        when(apiClient.getGeoResponse("Kyiv")).thenReturn(locationDto);
        when(apiClient.getWeatherResponse(50.45, 30.52)).thenReturn(responseDto);

        WeatherResponseDto result = weatherService.getWeatherResponseByCity("Kyiv");

        assertEquals("Europe/Kyiv", result.getTimezone());
        assertEquals(50.45, result.getLatitude());
        assertEquals(30.52, result.getLongitude());
    }

    @Test
    void updatedDataForWeather_shouldSaveDailyWeather_whenDataIsValid() {
        LocationEntity location = new LocationEntity();
        location.setCity("Kyiv");
        location.setLatitude(50.45);
        location.setLongitude(30.52);

        DailyWeatherDto daily = new DailyWeatherDto();
        daily.setTime(List.of("2026-04-02"));
        daily.setWeatherCodes(List.of(3));
        daily.setTempMax(List.of(18.0));
        daily.setTempMin(List.of(8.0));
        daily.setSunrise(List.of("06:20"));
        daily.setSunset(List.of("19:35"));
        daily.setUvMax(List.of(4.5));
        daily.setRainSum(List.of(0.7));

        WeatherResponseDto weatherResponse = new WeatherResponseDto();
        weatherResponse.setDaily(daily);

        when(locationRepository.findAll()).thenReturn(List.of(location));
        when(apiClient.getWeatherResponse(50.45, 30.52)).thenReturn(weatherResponse);
        when(savedDailyWeatherRepository.findByLocationAndTime(location, LocalDate.parse("2026-04-02")))
                .thenReturn(Optional.empty());
        when(savedDailyWeatherRepository.save(any(SavedDailyWeatherEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        weatherService.updatedDataForWeather();

        ArgumentCaptor<SavedDailyWeatherEntity> captor =
                ArgumentCaptor.forClass(SavedDailyWeatherEntity.class);

        verify(savedDailyWeatherRepository).save(captor.capture());
        verify(weatherStation).notifyObservers(any(SavedDailyWeatherEntity.class));

        SavedDailyWeatherEntity saved = captor.getValue();
        assertEquals(LocalDate.parse("2026-04-02"), saved.getTime());
        assertEquals(3, saved.getWeatherCodes());
        assertEquals(18.0, saved.getTempMax());
        assertEquals(8.0, saved.getTempMin());
        assertEquals("06:20", saved.getSunrise());
        assertEquals("19:35", saved.getSunset());
        assertEquals(4.5, saved.getUvMax());
        assertEquals(0.7, saved.getRainSum());
    }

    @Test
    void updatedDataForWeather_shouldNotSave_whenDailyTimeIsEmpty() {
        LocationEntity location = new LocationEntity();
        location.setLatitude(50.45);
        location.setLongitude(30.52);

        DailyWeatherDto daily = new DailyWeatherDto();
        daily.setTime(List.of());

        WeatherResponseDto weatherResponse = new WeatherResponseDto();
        weatherResponse.setDaily(daily);

        when(locationRepository.findAll()).thenReturn(List.of(location));
        when(apiClient.getWeatherResponse(50.45, 30.52)).thenReturn(weatherResponse);

        weatherService.updatedDataForWeather();

        verify(savedDailyWeatherRepository, never()).save(any(SavedDailyWeatherEntity.class));
    }

    @Test
    void updatedDataForWeather_shouldNotSave_whenExistingWeatherIsUnchanged() {
        LocationEntity location = new LocationEntity();
        location.setLatitude(50.45);
        location.setLongitude(30.52);
        location.setCity("Kyiv");

        SavedDailyWeatherEntity existing = new SavedDailyWeatherEntity();
        existing.setId(UUID.randomUUID());
        existing.setLocation(location);
        existing.setTime(LocalDate.parse("2026-04-02"));
        existing.setWeatherCodes(3);
        existing.setTempMax(18.0);
        existing.setTempMin(8.0);
        existing.setSunrise("06:20");
        existing.setSunset("19:35");
        existing.setUvMax(4.5);
        existing.setRainSum(0.7);

        DailyWeatherDto daily = new DailyWeatherDto();
        daily.setTime(List.of("2026-04-02"));
        daily.setWeatherCodes(List.of(3));
        daily.setTempMax(List.of(18.0));
        daily.setTempMin(List.of(8.0));
        daily.setSunrise(List.of("06:20"));
        daily.setSunset(List.of("19:35"));
        daily.setUvMax(List.of(4.5));
        daily.setRainSum(List.of(0.7));

        WeatherResponseDto weatherResponse = new WeatherResponseDto();
        weatherResponse.setDaily(daily);

        when(locationRepository.findAll()).thenReturn(List.of(location));
        when(apiClient.getWeatherResponse(50.45, 30.52)).thenReturn(weatherResponse);
        when(savedDailyWeatherRepository.findByLocationAndTime(location, LocalDate.parse("2026-04-02")))
                .thenReturn(Optional.of(existing));

        weatherService.updatedDataForWeather();

        verify(savedDailyWeatherRepository, never()).save(any(SavedDailyWeatherEntity.class));
        verify(weatherStation, never()).notifyObservers(any(SavedDailyWeatherEntity.class));
    }
}
