package com.example.weatherspringboot.dto;

import lombok.Data;

@Data
public class NotificationRulesDto {
    private Double minTemp;
    private Double maxTemp;
    private boolean notifyRain;
    private boolean notifySnow;
}