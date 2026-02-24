package com.example.weatherspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "location")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String cityName;
    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "currentLocation")
    private List<UsersDataEntity> users;


}
