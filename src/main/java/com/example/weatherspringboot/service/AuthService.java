package com.example.weatherspringboot.service;

import com.example.weatherspringboot.dto.LoginRequestDto;
import com.example.weatherspringboot.dto.UsersDataDto;
import com.example.weatherspringboot.entity.UsersDataEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String saveUserData(UsersDataDto usersData) {
        UsersDataEntity user = userService.register(usersData);
        List<String> roles = List.of(user.getRole().name());
        return jwtService.generateJwtToken(usersData.getEmail(), roles);
    }

    public String loginUser(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        return jwtService
                .generateJwtToken(authentication.getName(),
                        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

    }


}
