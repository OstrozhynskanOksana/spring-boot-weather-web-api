package com.example.weatherspringboot.service.Observer;

import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;

public interface WeatherSubject {
    void addObserver(WeatherObserver observer);
    void removeObserver(WeatherObserver observer);
    void notifyObservers(SavedDailyWeatherEntity savedDailyWeatherEntity);
}
