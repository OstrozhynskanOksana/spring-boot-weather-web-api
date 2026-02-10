package com.example.weatherspringboot.repository;

import com.example.weatherspringboot.dto.NotificationRules;
import com.example.weatherspringboot.entity.NotificationRulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRulesRepository extends JpaRepository<NotificationRulesEntity, Long> {
}
