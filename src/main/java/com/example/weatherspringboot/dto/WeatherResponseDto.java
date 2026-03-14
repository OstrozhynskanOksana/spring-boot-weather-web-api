package com.example.weatherspringboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponseDto {
    private double latitude;
    private double longitude;
    private String timezone;
    private CurrentWeatherDto current;
    private HourlyWeatherDto hourly;
    private DailyWeatherDto daily;
}