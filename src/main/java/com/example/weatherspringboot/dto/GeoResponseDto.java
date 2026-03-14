package com.example.weatherspringboot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeoResponseDto {
    private List<LocationDto> results;
}
