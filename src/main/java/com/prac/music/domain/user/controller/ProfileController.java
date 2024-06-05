package com.prac.music.domain.user.controller;

import com.prac.music.domain.user.dto.ProfileRequestDto;
import com.prac.music.domain.user.dto.ProfileResponseDto;
import com.prac.music.domain.user.service.ProfileService;
import com.prac.music.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto responseDto = profileService.getProfile(userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<String> updateProfile(@RequestBody @Valid ProfileRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String message = profileService.updateProfile(requestDto, userDetails.getUser());
        return ResponseEntity.ok(message);
    }
}
