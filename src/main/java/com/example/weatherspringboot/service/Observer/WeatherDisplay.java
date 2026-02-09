package com.example.weatherspringboot.service.Observer;

import com.example.weatherspringboot.entity.SavedWeatherDay;
import org.springframework.stereotype.Component;

@Component
public class WeatherDisplay implements  WeatherObserver {
    @Override
    public void updateWeather(SavedWeatherDay savedWeatherDay) {

    }
}
