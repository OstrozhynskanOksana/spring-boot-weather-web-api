package com.example.weatherspringboot.service.Observer;

import com.example.weatherspringboot.entity.SavedWeatherDay;
import org.springframework.stereotype.Component;

@Component
public interface WeatherObserver {
    void updateWeather(SavedWeatherDay savedWeatherDay);
}
