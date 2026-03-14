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

    @OneToMany(mappedBy = "savedDayEntity",  cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<EventEntity> eventEntity;
}