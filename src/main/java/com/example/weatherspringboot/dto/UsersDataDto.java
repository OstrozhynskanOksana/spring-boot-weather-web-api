package com.example.weatherspringboot.dto;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsersDataDto {
    private Long id;

    @NotBlank(message = "The field cannot be empty")
    @Email(message = "The email contains invalid characters")
    private String email;

    @NotBlank(message = "The field cannot be empty")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;
    private NotificationRules rules;
}
