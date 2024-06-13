package com.sparta.layered_unit_testing.domain.comment.dto;

import java.time.LocalDateTime;

import com.sparta.layered_unit_testing.domain.comment.entity.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
	private Long id;

	private String contents;

	private Long boardId;

	private Long userId;

	private LocalDateTime updatedAt;

	public CommentResponseDto(Comment comment) {
		this.id = comment.getId();
		this.contents = comment.getContents();
		this.boardId = comment.getBoard().getId();
		this.userId = comment.getUser().getId();
		this.updatedAt = comment.getCreatedAt();
	}
}
