package com.example.weatherspringboot.controller;

import com.example.weatherspringboot.dto.SavedPlanDto;
import com.example.weatherspringboot.service.SavedPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class SavedPlanController {
    private final SavedPlanService savedPlanService;

    @GetMapping
    public ResponseEntity<List<SavedPlanDto>> getPlans(Authentication authentication) {
        return ResponseEntity.ok(savedPlanService.getPlans(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<SavedPlanDto> addPlan(
            @RequestBody SavedPlanDto request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(savedPlanService.addPlan(authentication.getName(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable UUID id, Authentication authentication) {
        savedPlanService.deletePlan(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
