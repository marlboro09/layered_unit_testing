package com.sparta.layered_unit_testing.domain.user.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.layered_unit_testing.domain.user.dto.LoginRequestDto;
import com.sparta.layered_unit_testing.domain.user.dto.LoginResponseDto;
import com.sparta.layered_unit_testing.domain.user.dto.SignoutRequestDto;
import com.sparta.layered_unit_testing.domain.user.dto.SignupRequestDto;
import com.sparta.layered_unit_testing.domain.user.entity.User;
import com.sparta.layered_unit_testing.domain.user.security.UserDetailsImpl;
import com.sparta.layered_unit_testing.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<User> signup(@Valid @RequestPart(value = "user") SignupRequestDto requestDto,
		@RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
		return ResponseEntity.ok(userService.createUser(requestDto, file));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
		return ResponseEntity.ok(userService.loginUser(requestDto));
	}

	@PutMapping("/logout")
	public String logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		userService.logoutUser(user);
		return "redirect:/api/users/login";
	}

	@PutMapping("/signout")
	public String signout(@RequestBody SignoutRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		userService.signoutUser(requestDto, user);
		return "redirect:/api/users/login";
	}
}