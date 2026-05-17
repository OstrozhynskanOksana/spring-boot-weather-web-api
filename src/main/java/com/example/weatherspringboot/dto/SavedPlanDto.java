package com.example.weatherspringboot.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class SavedPlanDto {
    private UUID id;
    private LocalDate date;
    private String time;
    private String title;
    private String description;
    private String city;
    private Integer weatherCode;
    private Double tempMax;
    private Double tempMin;
    private Double rainSum;
}
