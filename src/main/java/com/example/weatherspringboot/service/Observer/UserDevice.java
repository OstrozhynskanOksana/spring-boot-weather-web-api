package com.example.weatherspringboot.service.Observer;

import com.example.weatherspringboot.entity.NotificationsRules;
import com.example.weatherspringboot.entity.SavedWeatherDay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDevice implements  WeatherObserver {


    @Override
    public void updateWeather(SavedWeatherDay savedWeatherDay) {

    }
}
