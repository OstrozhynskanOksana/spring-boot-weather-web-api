package com.example.weatherspringboot.service;

import com.example.weatherspringboot.Role;
import com.example.weatherspringboot.dto.LoginRequestDto;
import com.example.weatherspringboot.dto.UsersDataDto;
import com.example.weatherspringboot.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldReturnToken_whenUserRegistered() {
        UsersDataDto dto = new UsersDataDto();
        dto.setUsername("oksana");
        dto.setEmail("test@test.com");
        dto.setPassword("password123");

        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");
        user.setRole(Role.USER);

        when(userService.register(dto)).thenReturn(user);
        when(jwtService.generateJwtToken("test@test.com", List.of("USER")))
                .thenReturn("jwt-token");

        String result = authService.register(dto);

        assertEquals("jwt-token", result);
        verify(userService).register(dto);
        verify(jwtService).generateJwtToken("test@test.com", List.of("USER"));
    }

    @Test
    void loginUser_shouldReturnToken_whenCredentialsValid() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("test@test.com");
        dto.setPassword("password123");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "test@test.com",
                null,
                List.of(new SimpleGrantedAuthority("USER"))
        );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateJwtToken("test@test.com", List.of("USER")))
                .thenReturn("jwt-token");

        String result = authService.loginUser(dto);

        assertEquals("jwt-token", result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateJwtToken("test@test.com", List.of("USER"));
    }
}