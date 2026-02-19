package com.example.weatherspringboot.service;
import com.example.weatherspringboot.entity.UsersDataEntity;
import com.example.weatherspringboot.repository.UsersDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsImpService implements UserDetailsService {
    private final UsersDataRepository usersDataRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UsersDataEntity entity = usersDataRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
               String.format("User is not found: %s", username)));
        log.info("Loading user: {}", username);
        if (entity == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new User(entity.getEmail(), entity.getPassword(), Collections.emptyList());

    }
}