package com.sparta.layered_unit_testing.domain.board.dto;

import java.time.LocalDateTime;

import com.sparta.layered_unit_testing.domain.board.entity.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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