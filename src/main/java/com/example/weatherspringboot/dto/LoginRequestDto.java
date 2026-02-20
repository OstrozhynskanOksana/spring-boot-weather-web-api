package com.example.weatherspringboot.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;

}
