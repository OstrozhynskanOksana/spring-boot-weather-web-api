package com.example.weatherspringboot.service;

import com.example.weatherspringboot.dto.UsersDataDto;
import com.example.weatherspringboot.entity.UsersDataEntity;
import com.example.weatherspringboot.repository.UsersDataRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersDataRepository usersDataRepository;
    private final PasswordEncoder passwordEncoder;


    public void saveUserData(UsersDataDto usersData){
        var users = new UsersDataEntity();
        users.setEmail(usersData.getEmail());
        String encodedPassword = passwordEncoder.encode(usersData.getPassword());
        users.setPassword(encodedPassword);
        usersDataRepository.save(users);

    }



}
