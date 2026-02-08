package com.example.weatherspringboot.config;

import com.example.weatherspringboot.dto.GeoResponse;
import com.example.weatherspringboot.dto.LocationData;
import com.example.weatherspringboot.dto.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class ApiClient {

    private final RestTemplate restTemplate;

    public LocationData getGeoResponse(String city) {
      GeoResponse geoResponse = restTemplate.getForObject( "https://geocoding-api.open-meteo.com/v1/search?name="+city+"&count=10&format=json", GeoResponse.class);
        if(geoResponse == null || geoResponse.getResults().isEmpty()){
            throw new IllegalArgumentException("Location is not found");
        }
         return geoResponse.getResults().get(0);
     }




    public WeatherResponse getWeatherResponse(Double lat, Double lon) {
        WeatherResponse weatherResponse = restTemplate.getForObject("https://api.open-meteo.com/v1/forecast?latitude="+lat+"&longitude="+lon+"&daily=weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max,rain_sum&hourly=temperature_2m,rain,visibility,wind_speed_10m,uv_index,weather_code,relative_humidity_2m,is_day&current=temperature_2m,relative_humidity_2m,apparent_temperature,is_day,rain,weather_code,wind_speed_10m&timezone=auto", WeatherResponse.class);
        return weatherResponse;
    }


}