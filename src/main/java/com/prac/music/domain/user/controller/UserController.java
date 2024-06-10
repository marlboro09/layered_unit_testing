package com.prac.music.domain.user.controller;

import com.prac.music.domain.user.dto.LoginRequestDto;
import com.prac.music.domain.user.dto.LoginResponseDto;
import com.prac.music.domain.user.dto.SignoutRequestDto;
import com.prac.music.domain.user.dto.SignupRequestDto;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.security.UserDetailsImpl;
import com.prac.music.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid @RequestPart(value = "user") SignupRequestDto requestDto, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(userService.createUser(requestDto, file));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        System.out.println("로그인 시작");

        return ResponseEntity.ok(userService.loginUser(requestDto));
    }

    @PutMapping("/logout")
    public String logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        userService.logoutUser(user);
        return "redirect:/api/users/login";
    }

    @PutMapping("/signout")
    public String signout(@RequestBody SignoutRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        userService.signoutUser(requestDto, user);
        return "redirect:/api/users/login";
    }
}