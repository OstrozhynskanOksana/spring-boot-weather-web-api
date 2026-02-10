package com.example.weatherspringboot.service.Observer;


import com.example.weatherspringboot.entity.SavedWeatherDay;
import com.example.weatherspringboot.repository.NotificationRulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;




@Component
@RequiredArgsConstructor
public class UserDevice implements  WeatherObserver {
    private final NotificationRulesRepository notificationRulesRepository;
    private final WeatherStation weatherStation;

    @Override
    public void updateWeather(SavedWeatherDay savedWeatherDay) {
      notificationRulesRepository.findAll().forEach(notificationRulesEntity -> {
          if (notificationRulesEntity.getMinTemp() == null && notificationRulesEntity.getMaxTemp() == null) {
              return;
          }
              if (savedWeatherDay.getTempMin() < notificationRulesEntity.getMinTemp() ||
                      savedWeatherDay.getTempMax() > notificationRulesEntity.getMaxTemp() ||
                      notificationRulesEntity.isNotifyRain() && savedWeatherDay.getRainSum() > 0.0) {
                  weatherStation.notifyObservers(savedWeatherDay) ;


              }




      });


    }
}
