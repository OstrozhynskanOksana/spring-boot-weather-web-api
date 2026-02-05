package com.example.weatherspringboot.dto;

import lombok.Data;

@Data
public class WeatherResponse {
    private double latitude;
    private double longitude;
    private String timezone;
    private CurrentWeather current;
    private HourlyWeather hourly;
    private DailyWeather daily;
}