package com.example.weatherspringboot.service;

import com.example.weatherspringboot.entity.SavedWeatherDay;
import com.example.weatherspringboot.repository.SavedWeatherDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    public void send(SavedWeatherDay savedWeatherDay) {

       Double rain = savedWeatherDay.getRainSum();
            String message = "";
            if (rain >= 0.0 && rain < 0.3) {
            message = "no rain";
            }else if (rain >= 0.3 && rain < 1.0) {
            message = "light rain";
            }else if (rain >= 1.0 && rain < 1.5) {
                message = "moderate rain";
            }else if (rain >= 1.5 && rain < 5.0) {
                message = "heavy rain";
            }else if (rain >= 5.0 && rain < 15.0) {
                message = "pouring rain";
            }
            System.out.println("The weather has changed: min temperature - " + savedWeatherDay.getTempMin() +
                    ", max temperature - " + savedWeatherDay.getTempMax() + ", rain: " + message);


    }
}
