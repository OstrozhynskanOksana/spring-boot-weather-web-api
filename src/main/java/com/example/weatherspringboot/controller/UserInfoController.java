package com.example.weatherspringboot.controller;
import com.example.weatherspringboot.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/info")
    @PreAuthorize("#email==authentication.name")
    public ResponseEntity<?> getUserData(@RequestParam String email) {
        return ResponseEntity.ok().body(userInfoService.userInfo(email));


    }
}
