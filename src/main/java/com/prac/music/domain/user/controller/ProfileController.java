package com.prac.music.domain.user.controller;

import com.prac.music.domain.user.dto.ProfileRequestDto;
import com.prac.music.domain.user.dto.ProfileResponseDto;
import com.prac.music.domain.user.service.ProfileService;
import com.prac.music.domain.user.service.S3Service;
import com.prac.music.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
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
    private final S3Service s3Service;

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto responseDto = profileService.getProfile(userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }


    public ResponseEntity<String> updateProfile(@PathVariable String userId, @RequestPart(value = "user") @Valid ProfileRequestDto requestDto, @RequestPart(value = "file") MultipartFile file) throws IOException {
        String imageUrl = s3Service.s3Upload(file);
        String message = profileService.updateProfile(userId, requestDto,imageUrl);
        return ResponseEntity.ok(message);
    }
}