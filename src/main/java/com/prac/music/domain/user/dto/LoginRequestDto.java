package com.prac.music.domain.user.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginRequestDto {
    private String userId;

    private String password;
}