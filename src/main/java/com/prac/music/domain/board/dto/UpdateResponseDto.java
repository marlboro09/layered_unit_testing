package com.prac.music.domain.board.dto;

import java.time.LocalDateTime;

import com.prac.music.domain.board.entity.Board;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateResponseDto {

	private Long boardId;

	private Long userId;

	private String title;

	private String contents;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public UpdateResponseDto(Board board) {
		this.boardId = board.getId();
		this.userId = board.getUser().getId();
		this.title = board.getTitle();
		this.contents = board.getContents();
		this.createdAt = board.getCreateAt();
		this.updatedAt = board.getUpdatedAt();
	}
}
