package com.example.weatherspringboot.service;

import com.example.weatherspringboot.entity.SavedWeatherDay;
import com.example.weatherspringboot.repository.SavedWeatherDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SavedWeatherDayRepository savedWeatherDayRepository;

    public void send(SavedWeatherDay savedWeatherDay) {

        savedWeatherDayRepository.findAll().forEach(rainSum ->{
            String message = "";
            if (rainSum.getRainSum() == 0.0){
               message = "there is no rain";

            }if (rainSum.getRainSum() == 0.2) {
                message = "light rain";
            }if (rainSum.getRainSum() == 1.5) {
                message = "moderate rain";
            }if (rainSum.getRainSum() == 5.0) {
                message = "heavy rain";
            }if (rainSum.getRainSum() == 15.0) {
                message = "pouring rain";
            }
            System.out.println("The weather has changed: min temperature - " + savedWeatherDay.getTempMin() +
                    ", max temperature - " + savedWeatherDay.getTempMax() + ", rain: " + message);
        } );

    }
}
