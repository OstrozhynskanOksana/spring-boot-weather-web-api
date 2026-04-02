package com.example.weatherspringboot.controller;


import com.example.weatherspringboot.record.UserInfoResponse;
import com.example.weatherspringboot.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserInfoControllerTest {

    @Test
    void getUserData_shouldReturnUser() {
        UserInfoService userInfoService = mock(UserInfoService.class);
        UserInfoController controller = new UserInfoController(userInfoService);

        UserInfoResponse userInfoResponse = new UserInfoResponse(
                "oksana",
                "test@test.com"
        );

        when(userInfoService.userInfo("test@test.com")).thenReturn(userInfoResponse);

        ResponseEntity<?> response = controller.getUserData("test@test.com");

        assertEquals(200, response.getStatusCode().value());

        UserInfoResponse body = (UserInfoResponse) response.getBody();
        assertNotNull(body);
        assertEquals("test@test.com", body.email());
        assertEquals("oksana", body.username());

        verify(userInfoService).userInfo("test@test.com");
    }
}