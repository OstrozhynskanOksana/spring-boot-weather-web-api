package com.example.weatherspringboot.service;

import com.example.weatherspringboot.config.ApiClient;
import com.example.weatherspringboot.dto.CurrentWeather;
import com.example.weatherspringboot.dto.LocationData;
import com.example.weatherspringboot.dto.WeatherResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {


 private final ApiClient apiClient;



 public WeatherResponse getWeatherResponseByCity(String city) {
  LocationData dataGeoResponse = apiClient.getGeoResponse(city);
  WeatherResponse dataWeatherResponse = apiClient.getWeatherResponse(dataGeoResponse.getLatitude(), dataGeoResponse.getLongitude());
  return dataWeatherResponse;

 }
}


