package com.example.weatherspringboot.service;

import com.example.weatherspringboot.config.ApiClient;
import com.example.weatherspringboot.dto.DailyWeatherDto;
import com.example.weatherspringboot.dto.LocationDto;
import com.example.weatherspringboot.dto.WeatherResponseDto;
import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;
import com.example.weatherspringboot.repository.LocationRepository;
import com.example.weatherspringboot.repository.SavedDailyWeatherRepository;
import com.example.weatherspringboot.service.Observer.WeatherStation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {
    private final ApiClient apiClient;
    private final LocationRepository locationRepository;
    private final SavedDailyWeatherRepository savedDailyWeatherRepository;
    private final WeatherStation weatherStation;

    public WeatherResponseDto getWeatherResponseByCity(String city) {
        LocationDto dataGeoResponse = apiClient.getGeoResponse(city);
        return apiClient.getWeatherResponse(dataGeoResponse.getLatitude(), dataGeoResponse.getLongitude());
    }



    @Scheduled(cron = "0 */15 * * * *")
    @Transactional
    public void updatedDataForWeather(){
        locationRepository.findAll().forEach(location -> {
            WeatherResponseDto weatherResponseDtoData = apiClient.getWeatherResponse(
                    location.getLatitude(),
                    location.getLongitude()
            );

            DailyWeatherDto daily = weatherResponseDtoData.getDaily();
            if (daily.getTime() == null || daily.getTime().isEmpty()) {
                return;
            }

            for (int index = 0; index < daily.getTime().size(); index++) {
                LocalDate time = LocalDate.parse(daily.getTime().get(index));
                SavedDailyWeatherEntity entity = savedDailyWeatherRepository
                        .findByLocationAndTime(location, time)
                        .orElseGet(SavedDailyWeatherEntity::new);

                boolean isNew = entity.getId() == null;
                boolean isChanged = applyDailyWeather(entity, location, daily, index);
                if (!isNew && !isChanged) {
                    continue;
                }

                SavedDailyWeatherEntity savedEntity = savedDailyWeatherRepository.save(entity);
                weatherStation.notifyObservers(savedEntity);
            }
        });
    }

    private boolean applyDailyWeather(
            SavedDailyWeatherEntity entity,
            com.example.weatherspringboot.entity.LocationEntity location,
            DailyWeatherDto daily,
            int index
    ) {
        SavedDailyWeatherEntity incoming = new SavedDailyWeatherEntity();
        incoming.setLocation(location);
        incoming.setTime(LocalDate.parse(daily.getTime().get(index)));
        incoming.setWeatherCodes(valueAt(daily.getWeatherCodes(), index));
        incoming.setTempMax(valueAt(daily.getTempMax(), index));
        incoming.setTempMin(valueAt(daily.getTempMin(), index));
        incoming.setSunrise(valueAt(daily.getSunrise(), index));
        incoming.setSunset(valueAt(daily.getSunset(), index));
        incoming.setUvMax(valueAt(daily.getUvMax(), index));
        incoming.setRainSum(valueAt(daily.getRainSum(), index));

        boolean changed = !Objects.equals(entity.getLocation(), incoming.getLocation())
                || !Objects.equals(entity.getTime(), incoming.getTime())
                || !Objects.equals(entity.getWeatherCodes(), incoming.getWeatherCodes())
                || !Objects.equals(entity.getTempMax(), incoming.getTempMax())
                || !Objects.equals(entity.getTempMin(), incoming.getTempMin())
                || !Objects.equals(entity.getSunrise(), incoming.getSunrise())
                || !Objects.equals(entity.getSunset(), incoming.getSunset())
                || !Objects.equals(entity.getUvMax(), incoming.getUvMax())
                || !Objects.equals(entity.getRainSum(), incoming.getRainSum());

        entity.setLocation(incoming.getLocation());
        entity.setTime(incoming.getTime());
        entity.setWeatherCodes(incoming.getWeatherCodes());
        entity.setTempMax(incoming.getTempMax());
        entity.setTempMin(incoming.getTempMin());
        entity.setSunrise(incoming.getSunrise());
        entity.setSunset(incoming.getSunset());
        entity.setUvMax(incoming.getUvMax());
        entity.setRainSum(incoming.getRainSum());
        return changed;
    }

    private <T> T valueAt(List<T> values, int index) {
        if (values == null || values.size() <= index) {
            return null;
        }
        return values.get(index);
    }
}





