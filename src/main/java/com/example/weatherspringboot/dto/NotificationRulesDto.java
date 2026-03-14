package com.example.weatherspringboot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NotificationRulesDto {
    private UUID id;
    private Double minTemp;
    private Double maxTemp;
    private boolean notifyRain;

}