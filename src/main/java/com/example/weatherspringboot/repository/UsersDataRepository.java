package com.example.weatherspringboot.repository;

import com.example.weatherspringboot.entity.UsersDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;


public interface UsersDataRepository extends JpaRepository<UsersDataEntity, UUID> {
    Optional<UsersDataEntity> findByEmail(String username);


}
