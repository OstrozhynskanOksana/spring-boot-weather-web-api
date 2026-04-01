package com.example.weatherspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class WeatherSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherSpringBootApplication.class, args);
    }

}
