package com.example.weatherspringboot.controller;

import com.example.weatherspringboot.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/set-location")
    public ResponseEntity<String> setLocation(@RequestParam String email, @RequestParam String city) {
        locationService.setLocationByCity(email, city);
        return ResponseEntity.ok("Location set successfully");
    }
}
