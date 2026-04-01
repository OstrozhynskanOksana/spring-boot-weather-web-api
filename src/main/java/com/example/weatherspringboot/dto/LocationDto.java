package com.example.weatherspringboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto {
    private String name;
    private Double latitude;
    private Double longitude;

}
