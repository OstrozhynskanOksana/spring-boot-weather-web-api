package com.example.weatherspringboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRulesDto {
    private Double minTemp;
    private Double maxTemp;
    private boolean notifyRain;

}
