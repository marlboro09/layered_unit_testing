package com.prac.music.domain.board.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.prac.music.domain.board.entity.Board;
import com.prac.music.domain.comment.dto.CommentResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
	private Long boardId;

	private Long userId;

	private String title;

	private String contents;

	private LocalDateTime createdAt;

	private List<CommentResponseDto> comments;

	public BoardResponseDto(Board board) {
		this.boardId = board.getId();
		this.userId = board.getUser().getId();
		this.title = board.getTitle();
		this.contents = board.getContents();
		this.createdAt = board.getCreatedAt();
		this.comments = board.getComments().stream()
			.map(CommentResponseDto::new)
			.collect(Collectors.toList());
	}
}