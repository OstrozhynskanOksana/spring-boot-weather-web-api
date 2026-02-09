package com.example.weatherspringboot.service.Observer;

import com.example.weatherspringboot.entity.SavedWeatherDay;
import org.springframework.stereotype.Component;

@Component
public interface WeatherSubject {
    void addObserver(WeatherObserver observer);
    void removeObserver(WeatherObserver observer);
    void notifyObservers(SavedWeatherDay savedWeatherDay);
}
