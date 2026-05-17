package com.example.weatherspringboot.service.Observer;


import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;
import com.example.weatherspringboot.repository.SavedDayRepository;
import com.example.weatherspringboot.service.NotificationRulesService;
import com.example.weatherspringboot.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserDevice implements WeatherObserver {
    private final NotificationRulesService notificationRulesService;
    private final NotificationService notificationService;
    private final SavedDayRepository savedDayRepository;

    @Override
    @Transactional
    public void updateWeather(SavedDailyWeatherEntity weather) {
        notificationRulesService.getNotificationRules().forEach(rule -> {
            boolean isCold = rule.getMinTemp() != null && weather.getTempMin() < rule.getMinTemp();

            boolean isHot = rule.getMaxTemp() != null && weather.getTempMax() > rule.getMaxTemp();

            boolean isRaining = rule.isNotifyRain()
                    && weather.getRainSum() != null
                    && weather.getRainSum() > 0.0;

            if (isCold || isHot || isRaining) {
                rule.getUsers().forEach(user -> {
                    if (weather.getLocation() != null && weather.getTime() != null
                            && savedDayRepository.existsByUserAndWeatherDayLocationAndWeatherDayTime(
                                    user,
                                    weather.getLocation(),
                                    weather.getTime()
                            )) {
                        notificationService.send(user, weather);
                    }
                });
            }
        });
    }
}
