package com.example.weatherspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users_data")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String userName;

    @OneToOne
    @JoinColumn (name = "notifications_id")
    private NotificationsRules notifications;

    @ManyToOne
    @JoinColumn (name = "current_location_id")
    private LocationEntity currentLocation;


}
