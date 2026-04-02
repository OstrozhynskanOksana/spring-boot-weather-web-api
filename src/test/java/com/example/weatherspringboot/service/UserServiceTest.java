package com.example.weatherspringboot.service;

import com.example.weatherspringboot.Role;
import com.example.weatherspringboot.dto.UsersDataDto;
import com.example.weatherspringboot.entity.UserEntity;
import com.example.weatherspringboot.exception.EmailAlreadyExistsException;
import com.example.weatherspringboot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void save_shouldReturnSavedUser() {
        UserEntity user = new UserEntity();
        when(userRepository.save(user)).thenReturn(user);

        UserEntity result = userService.save(user);

        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @Test
    void findById_shouldReturnUser_whenUserExists() {
        UUID id = UUID.randomUUID();
        UserEntity user = new UserEntity();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserEntity result = userService.findById(id);

        assertEquals(id, result.getId());
        verify(userRepository).findById(id);
    }

    @Test
    void findById_shouldThrow_whenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.findById(id));
        assertEquals("User is not found", ex.getMessage());
    }

    @Test
    void findByEmail_shouldReturnUser_whenUserExists() {
        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        UserEntity result = userService.findByEmail("test@test.com");

        assertEquals("test@test.com", result.getEmail());
        verify(userRepository).findByEmail("test@test.com");
    }

    @Test
    void findByEmail_shouldThrow_whenUserNotFound() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.findByEmail("test@test.com")
        );

        assertEquals("User is not found: test@test.com", ex.getMessage());
    }

    @Test
    void register_shouldEncodePasswordAndSaveUser() {
        UsersDataDto dto = new UsersDataDto();
        dto.setUsername("oksana");
        dto.setEmail("test@test.com");
        dto.setPassword("password123");

        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");

        UserEntity savedUser = new UserEntity();
        savedUser.setUsername("oksana");
        savedUser.setEmail("test@test.com");
        savedUser.setPassword("encoded-password");
        savedUser.setRole(Role.USER);

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        UserEntity result = userService.register(dto);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());

        UserEntity userToSave = captor.getValue();
        assertEquals("oksana", userToSave.getUsername());
        assertEquals("test@test.com", userToSave.getEmail());
        assertEquals("encoded-password", userToSave.getPassword());
        assertEquals(Role.USER, userToSave.getRole());

        assertEquals("test@test.com", result.getEmail());
        assertEquals(Role.USER, result.getRole());
    }

    @Test
    void register_shouldThrowEmailAlreadyExistsException_whenDuplicateEmail() {
        UsersDataDto dto = new UsersDataDto();
        dto.setUsername("oksana");
        dto.setEmail("test@test.com");
        dto.setPassword("password123");

        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
        when(userRepository.save(any(UserEntity.class)))
                .thenThrow(new DataIntegrityViolationException("duplicate"));

        EmailAlreadyExistsException ex = assertThrows(
                EmailAlreadyExistsException.class,
                () -> userService.register(dto)
        );

        assertEquals("The email is already in use", ex.getMessage());
    }
}