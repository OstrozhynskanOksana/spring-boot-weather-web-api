package com.example.weatherspringboot.service;

import com.example.weatherspringboot.entity.NotificationRulesEntity;
import com.example.weatherspringboot.entity.UserEntity;
import com.example.weatherspringboot.repository.NotificationRulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationRulesService {
    private final NotificationRulesRepository notificationRulesRepository;
    private final UserService userService;


    public List<NotificationRulesEntity> getNotificationRules() {
       return notificationRulesRepository.findAll();
    }

    @Transactional
    public NotificationRulesEntity getRulesForUser(String email) {
        return userService.findByEmail(email).getNotifications();
    }

    @Transactional
    public NotificationRulesEntity setRulesForUser(String email, Double minTemp, Double maxTemp, boolean notifyRain) {
        UserEntity user = userService.findByEmail(email);
        NotificationRulesEntity rule = user.getNotifications();
        if (rule == null) {
            rule = new NotificationRulesEntity();
        }
        rule.setMinTemp(minTemp);
        rule.setMaxTemp(maxTemp);
        rule.setNotifyRain(notifyRain);
        notificationRulesRepository.save(rule);

        user.setNotifications(rule);
        userService.save(user);
        return rule;
    }
}
