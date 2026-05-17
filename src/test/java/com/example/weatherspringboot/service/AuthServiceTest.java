package com.example.weatherspringboot.service;

import com.example.weatherspringboot.Role;
import com.example.weatherspringboot.dto.LoginRequestDto;
import com.example.weatherspringboot.dto.UsersDataDto;
import com.example.weatherspringboot.entity.UserEntity;
import com.example.weatherspringboot.exception.EmailAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

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
        dto.setEmail(" Test@Test.com ");
        dto.setPassword("password123");

        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");
        user.setPassword("encoded-password");
        user.setRole(Role.USER);

        when(userService.findByEmail(" Test@Test.com ")).thenReturn(user);
        when(userService.passwordMatches(user, "password123")).thenReturn(true);
        when(jwtService.generateJwtToken("test@test.com", List.of("USER")))
                .thenReturn("jwt-token");

        String result = authService.loginUser(dto);

        assertEquals("jwt-token", result);
        verify(userService).findByEmail(" Test@Test.com ");
        verify(userService).passwordMatches(user, "password123");
        verify(jwtService).generateJwtToken("test@test.com", List.of("USER"));
    }

    @Test
    void register_shouldReturnToken_whenEmailAlreadyExistsAndPasswordMatches() {
        UsersDataDto dto = new UsersDataDto();
        dto.setUsername("oksana");
        dto.setEmail("test@test.com");
        dto.setPassword("password123");

        UserEntity existingUser = new UserEntity();
        existingUser.setEmail("test@test.com");
        existingUser.setRole(Role.USER);

        when(userService.register(dto)).thenThrow(new EmailAlreadyExistsException("The email is already in use"));
        when(userService.findByEmail("test@test.com")).thenReturn(existingUser);
        when(userService.passwordMatches(existingUser, "password123")).thenReturn(true);
        when(jwtService.generateJwtToken("test@test.com", List.of("USER"))).thenReturn("jwt-token");

        String result = authService.register(dto);

        assertEquals("jwt-token", result);
    }
}
