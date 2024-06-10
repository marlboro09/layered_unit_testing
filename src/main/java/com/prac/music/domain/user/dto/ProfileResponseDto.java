package com.prac.music.domain.user.dto;

import com.prac.music.domain.user.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String userId;  // ID

    private String name;  // 이름

    private String email;  // 이메일

    private String intro;  // 한줄소개

    public ProfileResponseDto(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.intro = user.getIntro();
    }
}