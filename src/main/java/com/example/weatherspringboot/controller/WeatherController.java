package com.example.weatherspringboot.controller;

import com.example.weatherspringboot.service.WeatherService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private WeatherService weatherService;
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

}
