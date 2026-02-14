package com.example.weatherspringboot.repository;

import com.example.weatherspringboot.entity.UsersDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UsersDataRepository extends JpaRepository<UsersDataEntity, UUID> {
    UsersDataEntity findByEmail(String username);
}
