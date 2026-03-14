package com.example.weatherspringboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentWeatherDto {
    private String time;
    private Integer interval;
    @JsonProperty("temperature_2m")
    private Double temperature;
    @JsonProperty("relative_humidity_2m")
    private Integer humidity;
    @JsonProperty("apparent_temperature")
    private Double feelsLike;
    @JsonProperty("is_day")
    private Integer isDay;
    private Double rain;
    @JsonProperty("weather_code")
    private Integer weatherCode;
    @JsonProperty("wind_speed_10m")
    private Double windSpeedInteger;
}