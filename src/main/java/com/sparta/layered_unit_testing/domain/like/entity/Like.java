package com.sparta.layered_unit_testing.domain.like.entity;

import com.sparta.layered_unit_testing.domain.user.entity.BaseTimeEntity;
import com.sparta.layered_unit_testing.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Like extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 유저의 ID
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	protected User user;

	// 게시글 또는 댓글의 ID
	public abstract Long getContentId();

	// 게시글 또는 댓글의 유형
	@Enumerated(EnumType.STRING)
	@Column(name = "content_type", nullable = false)
	protected ContentType contentType;
}