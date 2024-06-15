package com.sparta.layered_unit_testing.domain.user.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.layered_unit_testing.domain.user.dto.ProfileRequestDto;
import com.sparta.layered_unit_testing.domain.user.dto.ProfileResponseDto;
import com.sparta.layered_unit_testing.domain.user.security.UserDetailsImpl;
import com.sparta.layered_unit_testing.domain.user.service.ProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
	public ResponseEntity<String> updateProfile(@PathVariable Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestPart(value = "user") @Valid ProfileRequestDto requestDto,
		@RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
		String message = profileService.updateProfile(requestDto, userDetails.getUser(), file);
		return ResponseEntity.ok(message);
	}
}