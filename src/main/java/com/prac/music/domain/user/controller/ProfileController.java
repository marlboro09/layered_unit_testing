package com.prac.music.domain.user.controller;

import com.prac.music.domain.user.dto.ProfileRequestDto;
import com.prac.music.domain.user.dto.ProfileResponseDto;
import com.prac.music.domain.user.security.UserDetailsImpl;
import com.prac.music.domain.user.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfile(@PathVariable UserDetailsImpl userDetails, @RequestPart(value = "user") @Valid ProfileRequestDto requestDto, @RequestPart(value = "file") MultipartFile file) throws IOException {
        String message = profileService.updateProfile(requestDto, userDetails.getUser(), file);
        return ResponseEntity.ok(message);
    }
}