package com.example.weatherspringboot.service;

import com.example.weatherspringboot.entity.NotificationRulesEntity;
import com.example.weatherspringboot.entity.UserEntity;
import com.example.weatherspringboot.repository.NotificationRulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class NotificationRulesService {
    private final NotificationRulesRepository notificationRulesRepository;
    private final UserService userService;


    public List<NotificationRulesEntity> getNotificationRules() {
       return notificationRulesRepository.findAll();
    }

    @Transactional
    public NotificationRulesEntity setRules(UUID id, Double minTemp, Double maxTemp, boolean notifyRain) {
        NotificationRulesEntity rule = new NotificationRulesEntity();
        rule.setMinTemp(minTemp);
        rule.setMaxTemp(maxTemp);
        rule.setNotifyRain(notifyRain);
        notificationRulesRepository.save(rule);

        UserEntity user = userService.findById(id);
        user.setNotifications(rule);
        userService.saveUser(user);
        return rule;
    }
}
