package com.example.onboarding.controller;


import com.example.onboarding.dto.ProfileRequestDto;
import com.example.onboarding.dto.ProfileResponseDto;
import com.example.onboarding.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/{userId}")
    public ProfileResponseDto saveProfile(@PathVariable Long userId,
                                          @RequestBody ProfileRequestDto dto) {
        return profileService.saveProfile(userId, dto);
    }

    @GetMapping("/{userId}")
    public ProfileResponseDto getProfile(@PathVariable Long userId) {
        return profileService.getProfile(userId);
    }
}
