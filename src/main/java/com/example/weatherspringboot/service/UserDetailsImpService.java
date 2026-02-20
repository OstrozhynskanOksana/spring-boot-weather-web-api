package com.example.weatherspringboot.service;
import com.example.weatherspringboot.entity.UsersDataEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsImpService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UsersDataEntity entity = userService.findByEmail(username);
        log.info("Loading user: {}", username);

        return new CustomUserDetails(entity);

    }
}