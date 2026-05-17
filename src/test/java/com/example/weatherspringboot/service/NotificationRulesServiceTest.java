package com.example.weatherspringboot.service;

import com.example.weatherspringboot.entity.NotificationRulesEntity;
import com.example.weatherspringboot.entity.UserEntity;
import com.example.weatherspringboot.repository.NotificationRulesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationRulesServiceTest {
    @Mock
    private NotificationRulesRepository notificationRulesRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private NotificationRulesService notificationRulesService;

    @Test
    void setRulesForUser_shouldCreateRuleAndAttachItToUser() {
        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");

        when(userService.findByEmail("test@test.com")).thenReturn(user);

        NotificationRulesEntity result = notificationRulesService.setRulesForUser(
                "test@test.com",
                10.0,
                25.0,
                true
        );

        assertEquals(10.0, result.getMinTemp());
        assertEquals(25.0, result.getMaxTemp());
        assertTrue(result.isNotifyRain());
        assertEquals(result, user.getNotifications());
        verify(notificationRulesRepository).save(result);
        verify(userService).save(user);
    }
}
