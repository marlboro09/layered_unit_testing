package com.prac.music.domain.user.controller;

import com.prac.music.domain.user.dto.LoginRequestDto;
import com.prac.music.domain.user.dto.LoginResponseDto;
import com.prac.music.domain.user.dto.SignoutRequestDto;
import com.prac.music.domain.user.dto.SignupRequestDto;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.security.UserDetailsImpl;
import com.prac.music.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User Management")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    @Operation(requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data")))
    public ResponseEntity<User> signup(
        @RequestPart(value = "user") @Parameter(schema = @Schema(type = "string", format = "json")) SignupRequestDto requestDto,
        @RequestPart(value = "file") @Parameter(schema = @Schema(type = "string", format = "binary")) MultipartFile file) throws IOException {
        return ResponseEntity.ok(userService.createUser(requestDto, file));
    }

    @PostMapping("/login")
    @Operation(requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = LoginRequestDto.class))))
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(userService.loginUser(requestDto));
    }

    @PutMapping("/logout")
    @Operation
    public String logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        userService.logoutUser(user);
        return "redirect:/api/users/login";
    }

    @PutMapping("/signout")
    @Operation(requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = SignoutRequestDto.class))))
    public String signout(@RequestBody SignoutRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        userService.signoutUser(requestDto, user);
        return "redirect:/api/users/login";
    }
}
