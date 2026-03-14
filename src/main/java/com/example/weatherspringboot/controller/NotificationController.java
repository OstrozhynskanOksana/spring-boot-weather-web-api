package com.example.weatherspringboot.controller;

import com.example.weatherspringboot.dto.NotificationRulesDto;
import com.example.weatherspringboot.entity.NotificationRulesEntity;
import com.example.weatherspringboot.service.NotificationRulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRulesService notificationRulesService;


    @PostMapping("/set")
    public ResponseEntity<NotificationRulesEntity> addNotificationRules(@RequestBody NotificationRulesDto request) {
       NotificationRulesEntity savedRules = notificationRulesService.setRules(
               request.getId(),
               request.getMinTemp(),
               request.getMaxTemp(),
               request.isNotifyRain()
       );

        return ResponseEntity.ok(savedRules);
    }

}
