package com.example.weatherspringboot.service;

import com.example.weatherspringboot.config.ApiClient;
import com.example.weatherspringboot.dto.DailyWeather;
import com.example.weatherspringboot.dto.LocationData;
import com.example.weatherspringboot.dto.WeatherResponse;
import com.example.weatherspringboot.entity.SavedWeatherDay;
import com.example.weatherspringboot.repository.LocationRepository;
import com.example.weatherspringboot.repository.SavedWeatherDayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {


 private final ApiClient apiClient;
 private final LocationRepository locationRepository;
 private final SavedWeatherDayRepository savedWeatherDayRepository;


 public WeatherResponse getWeatherResponseByCity(String city) {
  LocationData dataGeoResponse = apiClient.getGeoResponse(city);
     return apiClient.getWeatherResponse(dataGeoResponse.getLatitude(), dataGeoResponse.getLongitude());

 }

@Scheduled(cron = "0 */15 * * * *")
 public void updatedDataForWeather(){
  locationRepository.findAll().forEach(location -> {
   WeatherResponse weatherResponseData = apiClient.getWeatherResponse(
           location.getLatitude(),
           location.getLongitude()
   );
   DailyWeather daily = weatherResponseData.getDaily();
   if (daily.getTime() == null || daily.getTime().isEmpty()) {
    return;
   }
   LocalDate date = LocalDate.parse(daily.getTime().getFirst());
   if(savedWeatherDayRepository.existsSavedWeatherDayBy(location, date)){
    return;
   }
   SavedWeatherDay entity = new SavedWeatherDay();
   entity.setWeatherCodes(daily.getWeatherCodes().getFirst());
   entity.setTempMax(daily.getTempMax().getFirst());
   entity.setTempMin(daily.getTempMin().getFirst());
   entity.setSunrise(daily.getSunrise().getFirst());
   entity.setSunset(daily.getSunset().getFirst());
   entity.setUvMax(daily.getUvMax().getFirst());
   entity.setRainSum(daily.getRainSum().getFirst());
   savedWeatherDayRepository.save(entity);

  });


  }

 }





