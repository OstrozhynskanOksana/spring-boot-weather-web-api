package com.example.weatherspringboot.service;

import com.example.weatherspringboot.entity.UsersDataEntity;
import com.example.weatherspringboot.repository.UsersDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersDataRepository usersDataRepository;

    public UsersDataEntity findByEmail(String email) {
        return usersDataRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format("User is not found: %s", email)
                        )
                );
    }

    public UsersDataEntity save(UsersDataEntity usersDataEntity) {
        try {
            return usersDataRepository.save(usersDataEntity);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Attempt to register duplicate email: {}", usersDataEntity.getEmail());
            throw new EmailAlreadyExistsException("The email is already in use");
        }
    }
}