package com.prac.music.domain.user.entity;

import com.prac.music.domain.user.dto.SignupRequestDto;
import com.prac.music.domain.user.dto.ProfileRequestDto;
import io.micrometer.common.util.StringUtils;

import jakarta.persistence.*;
import lombok.*;

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


    public boolean isExist() {
        return this.userStatusEnum == UserStatusEnum.NORMAL;
    }

    public User(SignupRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.intro = requestDto.getIntro();
        this.userStatusEnum = UserStatusEnum.NORMAL;
    }

    public void update(ProfileRequestDto requestDto) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.intro = requestDto.getIntro();
        // 비밀번호가 비어있지 않은 경우에만 업데이트
        if (StringUtils.isNotBlank(requestDto.getNewPassword())) {
            this.password = requestDto.getNewPassword();
        }
    }

    public void updateRefresh(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updateStatus(){
        this.userStatusEnum = UserStatusEnum.SECESSION;
    }

}