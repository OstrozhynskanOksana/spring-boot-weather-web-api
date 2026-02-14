package com.example.weatherspringboot.service;
import com.example.weatherspringboot.entity.UsersDataEntity;
import com.example.weatherspringboot.repository.UsersDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsSrvice implements UserDetailsService {
    private final UsersDataRepository usersDataRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UsersDataEntity entity = usersDataRepository.findByEmail(username);
        System.out.println("Loading user: " + username);
        if (entity == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new User(entity.getEmail(), entity.getPassword(), Collections.emptyList());

    }
}
