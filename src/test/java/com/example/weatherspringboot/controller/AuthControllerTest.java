package com.example.weatherspringboot.controller;

import com.example.weatherspringboot.dto.LoginRequestDto;
import com.example.weatherspringboot.dto.UsersDataDto;
import com.example.weatherspringboot.service.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        objectMapper = new ObjectMapper();

        AuthController controller = new AuthController(authService);

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(validator)
                .build();
    }

    @Test
    void signUp_shouldReturn200AndToken() throws Exception {
        UsersDataDto dto = new UsersDataDto();
        dto.setUsername("oksana");
        dto.setEmail("test@test.com");
        dto.setPassword("password123");

        when(authService.register(any(UsersDataDto.class))).thenReturn("jwt-token");

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));
    }

    @Test
    void signUp_shouldReturn400_whenBodyIsInvalid() throws Exception {
        UsersDataDto dto = new UsersDataDto();
        dto.setUsername("");
        dto.setEmail("wrong-email");
        dto.setPassword("123");

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signIn_shouldReturn200AndToken() throws Exception {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("test@test.com");
        dto.setPassword("password123");

        when(authService.loginUser(any(LoginRequestDto.class))).thenReturn("jwt-token");

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));
    }
}