package com.example.weatherspringboot.service.Observer;



import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WeatherStation implements WeatherSubject {

    private final List<WeatherObserver> observers;

    @Override
    public void addObserver(WeatherObserver observer) {
    observers.add(observer);
    }

    @Override
    public void removeObserver(WeatherObserver observer) {
    observers.remove(observer);
    }

    @Override
    public void notifyObservers(SavedDailyWeatherEntity savedDailyWeatherEntity) {
    for (WeatherObserver observer : observers) {
        observer.updateWeather(savedDailyWeatherEntity);
      }
    }


}
