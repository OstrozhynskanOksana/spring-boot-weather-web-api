package com.example.weatherspringboot.entity;

import com.example.weatherspringboot.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users_data")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 60)
    private String username;

    @Column(unique = true, nullable = false, length = 30)
    private String email;

    @Column(nullable = false,length = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "notifications_id")
    private NotificationRulesEntity notifications;

    @ManyToOne
    @JoinColumn (name = "current_location_id")
    private LocationEntity currentLocation;
}
