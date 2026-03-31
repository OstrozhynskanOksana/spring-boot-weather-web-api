package com.example.weatherspringboot.service;

import com.example.weatherspringboot.record.UserInfoResponse;
import com.example.weatherspringboot.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserService userService;

    public UserInfoResponse userInfo(String email) {
     UserEntity user = userService.findByEmail(email);

     return new UserInfoResponse(
             user.getUsername(),
             user.getEmail()
     );
 }
}
