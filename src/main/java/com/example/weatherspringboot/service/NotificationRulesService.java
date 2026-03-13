package com.example.weatherspringboot.service;

import com.example.weatherspringboot.entity.NotificationRulesEntity;
import com.example.weatherspringboot.repository.NotificationRulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationRulesService {
    private final NotificationRulesRepository notificationRulesRepository;

    public List<NotificationRulesEntity> getNotificationRules() {
       return notificationRulesRepository.findAll();
    }
}
