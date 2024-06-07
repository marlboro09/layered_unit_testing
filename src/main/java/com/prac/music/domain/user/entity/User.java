package com.prac.music.domain.user.entity;

import com.prac.music.domain.user.dto.SignupRequestDto;
import com.prac.music.domain.user.dto.ProfileRequestDto;
import io.micrometer.common.util.StringUtils;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Getter
@Table(name = "user")
@NoArgsConstructor
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

    public User(SignupRequestDto requestDto,String profileImage) {
        this.userId = requestDto.getUserId();
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.intro = requestDto.getIntro();
        this.profileImage = profileImage;
        this.userStatusEnum = UserStatusEnum.NORMAL;
    }

    public void update(ProfileRequestDto requestDto, String profileImage) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.intro = requestDto.getIntro();
        this.profileImage = profileImage;
        // 비밀번호가 비어있지 않은 경우에만 업데이트
        if (StringUtils.isNotBlank(requestDto.getNewPassword())) {
            this.password = requestDto.getNewPassword();
        }
    }
}