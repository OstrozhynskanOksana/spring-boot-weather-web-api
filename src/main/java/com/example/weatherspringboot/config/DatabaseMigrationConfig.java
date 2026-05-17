package com.example.weatherspringboot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class DatabaseMigrationConfig {
    private final JdbcTemplate jdbcTemplate;

    @Bean
    ApplicationRunner widenEmailColumn() {
        return args -> jdbcTemplate.execute("alter table if exists users_data alter column email type varchar(254)");
    }
}
