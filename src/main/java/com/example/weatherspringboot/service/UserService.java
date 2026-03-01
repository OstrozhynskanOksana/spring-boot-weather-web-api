package com.example.weatherspringboot.service;

import com.example.weatherspringboot.Role;
import com.example.weatherspringboot.dto.UsersDataDto;
import com.example.weatherspringboot.entity.UsersDataEntity;
import com.example.weatherspringboot.repository.UsersDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersDataRepository usersDataRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersDataEntity findByEmail(String email) {
        return usersDataRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format("User is not found: %s", email)
                        )
                );
    }


    public UsersDataEntity register(UsersDataDto usersData) {
        try {
            var users = new UsersDataEntity();
            users.setEmail(usersData.getEmail());
            String encodedPassword = passwordEncoder.encode(usersData.getPassword());
            users.setPassword(encodedPassword);
            users.setRole(Role.USER);

            return usersDataRepository.save(users);

        } catch (DataIntegrityViolationException ex) {
            log.warn("Attempt to register duplicate email: {}", usersData.getEmail());
            throw new EmailAlreadyExistsException("The email is already in use");
        }
    }

}