package com.example.weatherspringboot.service;

import com.example.weatherspringboot.config.ApiClient;
import com.example.weatherspringboot.dto.DailyWeatherDto;
import com.example.weatherspringboot.dto.LocationDto;
import com.example.weatherspringboot.dto.WeatherResponseDto;
import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;
import com.example.weatherspringboot.repository.LocationRepository;
import com.example.weatherspringboot.repository.SavedDailyWeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {


 private final ApiClient apiClient;
 private final LocationRepository locationRepository;
 private final SavedDailyWeatherRepository savedDailyWeatherRepository;

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
   LocalDate date = LocalDate.parse(daily.getTime().getFirst());
   if(savedDailyWeatherRepository.existsSavedWeatherDayBy(location, date)){
    return;
   }
   SavedDailyWeatherEntity entity = new SavedDailyWeatherEntity();
   entity.setWeatherCodes(daily.getWeatherCodes().getFirst());
   entity.setTempMax(daily.getTempMax().getFirst());
   entity.setTempMin(daily.getTempMin().getFirst());
   entity.setSunrise(daily.getSunrise().getFirst());
   entity.setSunset(daily.getSunset().getFirst());
   entity.setUvMax(daily.getUvMax().getFirst());
   entity.setRainSum(daily.getRainSum().getFirst());
   savedDailyWeatherRepository.save(entity);

  });


  }

 }





