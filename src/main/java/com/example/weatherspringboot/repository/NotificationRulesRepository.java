package com.example.weatherspringboot.repository;

import com.example.weatherspringboot.entity.NotificationRulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface NotificationRulesRepository extends JpaRepository<NotificationRulesEntity, UUID> {
}
