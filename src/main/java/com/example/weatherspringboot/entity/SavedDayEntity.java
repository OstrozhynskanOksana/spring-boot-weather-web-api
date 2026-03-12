package com.example.weatherspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "saved_day")
public class SavedDayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String note;

    @ManyToOne(optional = false)
    private UserEntity user;

    @ManyToOne(optional = false)
    private SavedDailyWeatherEntity weatherDay;

    @OneToMany(mappedBy = "savedDay",  cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<EventEntity> eventEntity;
}