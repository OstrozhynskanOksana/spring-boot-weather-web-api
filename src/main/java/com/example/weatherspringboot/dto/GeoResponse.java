package com.example.weatherspringboot.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeoResponse {
    private List<LocationData> results;
}
