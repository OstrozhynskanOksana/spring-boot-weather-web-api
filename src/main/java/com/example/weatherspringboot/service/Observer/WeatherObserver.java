package com.example.weatherspringboot.service.Observer;

import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;

public interface WeatherObserver {
    void updateWeather(SavedDailyWeatherEntity savedDailyWeatherEntity);
}
