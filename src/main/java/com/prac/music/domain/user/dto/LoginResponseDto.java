package com.prac.music.domain.user.dto;

import com.prac.music.domain.user.entity.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String userId;

    private String password;

    private String name;

    private String email;

    private String intro;

    private String token;


    public LoginResponseDto(User user, String token) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
        this.intro = user.getIntro();
        this.token = token;
    }
}
