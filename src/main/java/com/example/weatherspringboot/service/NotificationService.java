package com.example.weatherspringboot.service;

import com.example.weatherspringboot.entity.NotificationRulesEntity;
import com.example.weatherspringboot.entity.SavedDailyWeatherEntity;
import com.example.weatherspringboot.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    public void send(UserEntity user, SavedDailyWeatherEntity savedDailyWeatherEntity) {
        NotificationRulesEntity rules = user.getNotifications();
        if (rules == null) {
            return;
        }

        List<String> reasons = new ArrayList<>();
        if (rules.getMinTemp() != null
                && savedDailyWeatherEntity.getTempMin() != null
                && savedDailyWeatherEntity.getTempMin() < rules.getMinTemp()) {
            reasons.add("below your comfort minimum of " + rules.getMinTemp() + " C");
        }
        if (rules.getMaxTemp() != null
                && savedDailyWeatherEntity.getTempMax() != null
                && savedDailyWeatherEntity.getTempMax() > rules.getMaxTemp()) {
            reasons.add("above your comfort maximum of " + rules.getMaxTemp() + " C");
        }
        if (rules.isNotifyRain()
                && savedDailyWeatherEntity.getRainSum() != null
                && savedDailyWeatherEntity.getRainSum() > 0.0) {
            reasons.add("rain expected: " + describeRain(savedDailyWeatherEntity.getRainSum()));
        }

        if (reasons.isEmpty()) {
            return;
        }

        System.out.println("Weather alert for " + user.getEmail() + ": " + String.join(", ", reasons)
                + ". Forecast min " + savedDailyWeatherEntity.getTempMin()
                + " C, max " + savedDailyWeatherEntity.getTempMax()
                + " C.");
    }

    private String describeRain(Double rain) {
        if (rain == null || rain < 0.3) {
            return "very light rain";
        } else if (rain < 1.0) {
            return "light rain";
        } else if (rain < 1.5) {
            return "moderate rain";
        } else if (rain < 5.0) {
            return "heavy rain";
        }
        return "pouring rain";
    }
}
