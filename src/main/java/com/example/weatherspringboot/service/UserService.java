package com.example.weatherspringboot.service;

import com.example.weatherspringboot.entity.UsersDataEntity;
import com.example.weatherspringboot.repository.UsersDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersDataRepository usersDataRepository;

    public UsersDataEntity findByEmail(String email) {
        return usersDataRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User is not found: %s", email))
        );

    }

    public UsersDataEntity save(UsersDataEntity usersDataEntity) {
        return usersDataRepository.save(usersDataEntity);
        //TO DO...

    }
}
