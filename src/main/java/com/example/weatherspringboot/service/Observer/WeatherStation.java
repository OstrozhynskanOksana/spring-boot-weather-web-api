package com.example.weatherspringboot.service.Observer;



import com.example.weatherspringboot.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WeatherStation implements WeatherSubject {

    private final WeatherService weatherService;
    private List<WeatherObserver> observers;
    private String data;


    public void setData(String data){
        this.data = data;
        notifyObservers();
    }

    @Override
    public void addObserver(WeatherObserver observer) {
    observers.add(observer);
    }

    @Override
    public void removeObserver(WeatherObserver observer) {
    observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
    for (WeatherObserver observer : observers) {
        observer.updateWeather();
      }
    }
}
