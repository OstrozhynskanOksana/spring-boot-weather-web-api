package com.example.weatherspringboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table
public class SavedWeatherDay {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate time;

    private Integer weatherCodes;

    private Double tempMax;

    private Double tempMin;

    private String sunrise;

    private String sunset;

    private Double uvMax;

    private Double rainSum;



}