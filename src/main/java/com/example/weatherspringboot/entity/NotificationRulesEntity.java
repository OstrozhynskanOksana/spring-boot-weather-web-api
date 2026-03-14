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
@Table(name = "notifications_rules")
public class NotificationRulesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "min_temperature")
    private Double minTemp;

    @Column(name = "max_temperature")
    private Double maxTemp;

    @Column(name = "notify_rain")
    private boolean notifyRain;


@OneToMany(mappedBy = "notifications")
    private List<UserEntity> users;


}
