package com.example.weatherspringboot.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeoResponseDto {
    private List<LocationDto> results;
}
