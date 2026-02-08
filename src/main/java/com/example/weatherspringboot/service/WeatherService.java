package com.example.weatherspringboot.service;

import com.example.weatherspringboot.config.ApiClient;
import com.example.weatherspringboot.dto.DailyWeather;
import com.example.weatherspringboot.dto.LocationData;
import com.example.weatherspringboot.dto.WeatherResponse;
import com.example.weatherspringboot.entity.SavedWeatherDay;
import com.example.weatherspringboot.repository.LocationRepository;
import com.example.weatherspringboot.repository.SavedWeatherDayRepository;
import com.example.weatherspringboot.service.Observer.WeatherStation;
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
 private final WeatherStation weatherStation;
 private final SavedWeatherDayRepository savedWeatherDayRepository;


 public WeatherResponse getWeatherResponseByCity(String city) {
  LocationData dataGeoResponse = apiClient.getGeoResponse(city);
  WeatherResponse dataWeatherResponse = apiClient.getWeatherResponse(dataGeoResponse.getLatitude(), dataGeoResponse.getLongitude());
  return dataWeatherResponse;

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
   LocalDate date = LocalDate.parse(daily.getTime().get(0));
   if(savedWeatherDayRepository.existsSavedWeatherDayBy(location, date)){
    return;
   }
   SavedWeatherDay entity = new SavedWeatherDay();
   entity.setWeatherCodes(daily.getWeatherCodes().get(0));
   entity.setTempMax(daily.getTempMax().get(0));
   entity.setTempMin(daily.getTempMin().get(0));
   entity.setSunrise(daily.getSunrise().get(0));
   entity.setSunset(daily.getSunset().get(0));
   entity.setUvMax(daily.getUvMax().get(0));
   entity.setRainSum(daily.getRainSum().get(0));
   savedWeatherDayRepository.save(entity);

  });


  }

 }





