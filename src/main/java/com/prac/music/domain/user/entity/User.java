package com.prac.music.domain.user.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(nullable = false, name = "createdAt")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(nullable = false, name = "deletedAt")
    private LocalDateTime deletedAt;
}
