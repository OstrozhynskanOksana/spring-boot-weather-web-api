package com.example.weatherspringboot.repository;

import com.example.weatherspringboot.entity.UsersDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersDataRepository extends JpaRepository<UsersDataEntity, UUID> {
    Optional<UsersDataEntity> findByEmail(String email);


}
