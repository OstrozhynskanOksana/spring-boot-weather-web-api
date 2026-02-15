package com.example.weatherspringboot.repository;

import com.example.weatherspringboot.entity.UsersDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;


public interface UsersDataRepository extends JpaRepository<UsersDataEntity, UUID> {
    UsersDataEntity findByEmail(String username);
}
