package com.example.weatherspringboot.dto;

import com.example.weatherspringboot.ContactType;
import lombok.Data;

@Data
public class UsersData {
    private Long id;
    private String userName;
    private ContactType contactType;
    private NotificationRules rules;
}
