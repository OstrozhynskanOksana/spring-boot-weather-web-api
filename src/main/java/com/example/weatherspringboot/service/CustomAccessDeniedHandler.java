package com.example.weatherspringboot.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json; charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = Map.of(
                "error", "Forbidden",
                "message", "Access is denied",
                "path", request.getRequestURI()
        );
        objectMapper.writeValue(response.getWriter(), map);

    }
}
