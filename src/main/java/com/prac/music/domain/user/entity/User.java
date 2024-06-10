package com.prac.music.domain.user.entity;

import com.prac.music.domain.user.dto.ProfileRequestDto;
import com.prac.music.domain.user.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "userid")
    private String userId;

    @Column(unique = true, nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "intro")
    private String intro;

    @Column(nullable = false, name = "status")
    private UserStatusEnum userStatusEnum;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "profile_Image")
    private String profileImage;


    public boolean isExist() {
        return this.userStatusEnum == UserStatusEnum.NORMAL;
    }

    public User(SignupRequestDto requestDto, String profileImage) {
        this.userId = requestDto.getUserId();
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.intro = requestDto.getIntro();
        this.profileImage = profileImage;
        this.userStatusEnum = UserStatusEnum.TEMPORARY;
    }

    public void profileUpdate(ProfileRequestDto requestDto, String profileImage) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.intro = requestDto.getIntro();
        this.profileImage = profileImage;
    }

    public void profileUpdate(ProfileRequestDto requestDto, String encodedPasswdDto, String profileImage) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.intro = requestDto.getIntro();
        this.password = encodedPasswdDto;
        this.profileImage = profileImage;
    }

    public void updateRefresh(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateStatusVeryfied() {
        this.userStatusEnum = UserStatusEnum.NORMAL;
    }

    public void updateStatusSignout() {
        this.userStatusEnum = UserStatusEnum.SECESSION;
    }

}
