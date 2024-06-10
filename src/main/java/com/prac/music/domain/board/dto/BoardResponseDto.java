package com.prac.music.domain.board.dto;

import com.prac.music.domain.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
	private Long boardId;

	private Long userId;

	private String title;

	private String contents;

	private LocalDateTime updatedAt;

	public BoardResponseDto(Board board) {
		this.boardId = board.getId();
		this.userId = board.getUser().getId();
		this.title = board.getTitle();
		this.contents = board.getContents();
		this.updatedAt = board.getCreatedAt();
	}
}