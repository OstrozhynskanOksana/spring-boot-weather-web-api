package com.example.weatherspringboot.controller;

import com.example.weatherspringboot.dto.NotificationRulesDto;
import com.example.weatherspringboot.entity.NotificationRulesEntity;
import com.example.weatherspringboot.service.NotificationRulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification-rules")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRulesService notificationRulesService;


    @PostMapping
    public ResponseEntity<NotificationRulesDto> addNotificationRules(
            @RequestBody NotificationRulesDto request,
            Authentication authentication
    ) {
       NotificationRulesEntity savedRules = notificationRulesService.setRulesForUser(
               authentication.getName(),
               request.getMinTemp(),
               request.getMaxTemp(),
               request.isNotifyRain()
       );

        return ResponseEntity.ok(toDto(savedRules));
    }

    @GetMapping
    public ResponseEntity<NotificationRulesDto> getNotificationRules(Authentication authentication) {
        NotificationRulesEntity rules = notificationRulesService.getRulesForUser(authentication.getName());
        if (rules == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(toDto(rules));
    }

    private NotificationRulesDto toDto(NotificationRulesEntity rules) {
        NotificationRulesDto dto = new NotificationRulesDto();
        dto.setMinTemp(rules.getMinTemp());
        dto.setMaxTemp(rules.getMaxTemp());
        dto.setNotifyRain(rules.isNotifyRain());
        return dto;
    }

}
