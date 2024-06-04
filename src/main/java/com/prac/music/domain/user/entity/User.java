package com.prac.music.domain.user.entity;

import com.prac.music.domain.user.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseTimeEntity{
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

    public User(SignupRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.intro = requestDto.getIntro();
        this.userStatusEnum = UserStatusEnum.NORMAL;
    }
}
