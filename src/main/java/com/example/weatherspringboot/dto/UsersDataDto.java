package com.example.weatherspringboot.dto;

import lombok.Data;

@Data
public class UsersDataDto {
    private Long id;
    private String email;
    private String password;
    private NotificationRules rules;
}
