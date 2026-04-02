package com.example.weatherspringboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "location")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String city;
    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "currentLocation")
    private List<UserEntity> users;

}
