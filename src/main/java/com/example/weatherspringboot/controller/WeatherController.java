package com.example.weatherspringboot.controller;

import com.example.weatherspringboot.dto.WeatherResponse;
import com.example.weatherspringboot.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/auth/")
    public WeatherResponse getWeatherByCity(@RequestParam String city) {
    log.info("get weather by city: {}", city);
        return weatherService.getWeatherResponseByCity(city);

    }


}
