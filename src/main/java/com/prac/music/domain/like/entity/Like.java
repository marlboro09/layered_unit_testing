package com.prac.music.domain.like.entity;

import com.prac.music.domain.user.entity.BaseTimeEntity;
import com.prac.music.domain.user.entity.User;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Like extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저의 ID
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 게시글 또는 댓글의 ID
    public abstract Long getContentId();

    // 게시글 또는 댓글의 유형
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

}