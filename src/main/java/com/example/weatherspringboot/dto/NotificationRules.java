package com.example.weatherspringboot.dto;

import lombok.Data;

@Data
public class NotificationRules {
    private Double minTemp;
    private Double maxTemp;
    private boolean notifyRain;
    private boolean notifySnow;
}