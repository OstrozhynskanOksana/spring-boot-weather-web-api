package com.example.weatherspringboot.service;

import com.example.weatherspringboot.Role;
import com.example.weatherspringboot.dto.LoginRequestDto;
import com.example.weatherspringboot.dto.UsersDataDto;
import com.example.weatherspringboot.entity.UsersDataEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String saveUserData(UsersDataDto usersData) {

        var users = new UsersDataEntity();
        users.setEmail(usersData.getEmail());
        String encodedPassword = passwordEncoder.encode(usersData.getPassword());
        users.setPassword(encodedPassword);
        users.setRole(Role.USER);
        userService.save(users);



        return jwtService.generateJwtToken(users.getEmail(), List.of());
    }

    public String loginUser(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(
                GrantedAuthority::getAuthority).toList();

        return jwtService
                .generateJwtToken(userDetails.getUsername(),
                        roles);

    }


}
