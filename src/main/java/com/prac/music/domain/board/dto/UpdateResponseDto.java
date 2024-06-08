package com.prac.music.domain.board.dto;

import com.prac.music.domain.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
		this.createdAt = board.getCreatedAt();
		this.updatedAt = board.getUpdatedAt();
	}
}
