package com.example.weatherspringboot.service.Observer;


import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;
import com.example.weatherspringboot.service.NotificationRulesService;
import com.example.weatherspringboot.service.NotificationService;
import com.example.weatherspringboot.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserDevice implements WeatherObserver {
    private final NotificationRulesService notificationRulesService;
    private final NotificationService notificationService;
    private final UserService userService;

    @Override
    @Transactional
    public void updateWeather(SavedDailyWeatherEntity weather) {
        notificationRulesService.getNotificationRules().forEach(rule -> {
            boolean isCold = rule.getMinTemp() != null && weather.getTempMin() < rule.getMinTemp();

            boolean isHot = rule.getMaxTemp() != null && weather.getTempMax() > rule.getMaxTemp();

            boolean isRaining = rule.isNotifyRain() && weather.getRainSum() > 0.0;

            if (isCold || isHot || isRaining) {
                rule.getUsers().forEach(user -> {
                    if(user.getCurrentLocation() == weather.getLocation()) {}
                    notificationService.send(user, weather);
                });
            }
        });
    }
}
