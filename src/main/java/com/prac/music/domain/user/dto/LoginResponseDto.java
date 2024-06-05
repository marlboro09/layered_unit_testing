package com.prac.music.domain.user.dto;

import com.prac.music.domain.user.entity.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String token;

    private String message;

    public LoginResponseDto(String token, String message) {
        this.token = token;
        this.message = message;
    }
}
