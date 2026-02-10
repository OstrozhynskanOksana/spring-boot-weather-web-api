package com.example.weatherspringboot.service.Observer;


import com.example.weatherspringboot.entity.SavedWeatherDay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherDisplay implements  WeatherObserver {



    @Override
    public void updateWeather(SavedWeatherDay savedWeatherDay) {

        System.out.println("updating weather for day " + savedWeatherDay);
    }
}
