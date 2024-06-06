package com.prac.music.domain.user.controller;

import com.prac.music.domain.user.dto.ProfileRequestDto;
import com.prac.music.domain.user.dto.ProfileResponseDto;
import com.prac.music.domain.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/profile")
public class ProfileController {

    private final ProfileService profileService;

    // **
    // 이후 Token 을 이용해 인증된 사용자의 정보를 가져오도록 코드 변경 예정
    // 로그인한 사용자 ID 로 프로필 조회 및 수정
    // 맵핑 주소 확정 X ( JWT 적용에 따라 바뀔 예정 )
    // */

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable String userId) {
        ProfileResponseDto responseDto = profileService.getProfile(userId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<String> updateProfile(@PathVariable String userId, @RequestBody @Valid ProfileRequestDto requestDto) {
        String message = profileService.updateProfile(userId, requestDto);
        return ResponseEntity.ok(message);
    }
}