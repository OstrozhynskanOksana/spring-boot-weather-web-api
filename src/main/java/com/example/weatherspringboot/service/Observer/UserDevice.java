package com.example.weatherspringboot.service.Observer;


import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;
import com.example.weatherspringboot.repository.NotificationRulesRepository;
import com.example.weatherspringboot.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;




@Component
@RequiredArgsConstructor
public class UserDevice implements  WeatherObserver {
    private final NotificationRulesRepository notificationRulesRepository;
    private final NotificationService notificationService;

    @Override
    public void updateWeather(SavedDailyWeatherEntity savedDailyWeatherEntity) {
      notificationRulesRepository.findAll().forEach(notificationRulesEntity -> {
          if (notificationRulesEntity.getMinTemp() == null && notificationRulesEntity.getMaxTemp() == null) {
              return;
          }
              if (savedDailyWeatherEntity.getTempMin() < notificationRulesEntity.getMinTemp() ||
                      savedDailyWeatherEntity.getTempMax() > notificationRulesEntity.getMaxTemp() ||
                      notificationRulesEntity.isNotifyRain() && savedDailyWeatherEntity.getRainSum() > 0.0) {
                  notificationService.send(savedDailyWeatherEntity);


              }




      });


    }
}
