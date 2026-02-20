package com.example.weatherspringboot.controller;

import com.example.weatherspringboot.dto.LoginRequestDto;
import com.example.weatherspringboot.dto.UsersDataDto;
import com.example.weatherspringboot.service.AuthService;
import com.example.weatherspringboot.service.CustomUserDetails;
import com.example.weatherspringboot.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UsersDataDto usersData){
        log.info("User is registered with email: {}", usersData.getEmail());
        authService.saveUserData(usersData);
        return ResponseEntity.ok().build();


    }
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody LoginRequestDto loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generateJwtToken(userDetails.getUsername());
        return  ResponseEntity.ok().build();
    }


}
