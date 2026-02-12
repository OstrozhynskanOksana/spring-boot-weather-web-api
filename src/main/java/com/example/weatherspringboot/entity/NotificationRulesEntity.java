package com.example.weatherspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
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

    private boolean notifyRain;


@OneToMany(mappedBy = "notifications")
    private List<UsersDataEntity> users;


}
