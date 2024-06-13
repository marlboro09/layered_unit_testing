package com.sparta.layered_unit_testing.domain.comment.entity;

import com.sparta.layered_unit_testing.domain.like.entity.ContentType;
import com.sparta.layered_unit_testing.domain.like.entity.Like;
import com.sparta.layered_unit_testing.domain.like.entity.Sharer;
import com.sparta.layered_unit_testing.domain.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment_like")
@NoArgsConstructor
public class CommentLike extends Like implements Sharer {
	@ManyToOne
	@JoinColumn(name = "comment_id", nullable = false)
	private Comment comment;

	@Override
	public Long getContentId() {
		return comment.getId();
	}

	@Override
	public User getUser() {
		return user;
	}

	public CommentLike(Comment comment, User user) {
		this.comment = comment;
		this.user = user;
		this.contentType = ContentType.COMMENT;
	}
}
