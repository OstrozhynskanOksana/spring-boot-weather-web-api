package com.example.weatherspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users_data")
public class UsersDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, length = 30)
    private String email;

    @Column(nullable = false,length = 60)
    private String password;

    @ManyToOne
    @JoinColumn (name = "notifications_id")
    private NotificationRulesEntity notifications;

    @ManyToOne
    @JoinColumn (name = "current_location_id")
    private LocationEntity currentLocation;


}
