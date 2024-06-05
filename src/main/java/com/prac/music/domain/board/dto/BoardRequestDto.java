package com.prac.music.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardRequestDto {

	private Long boardId;

	private String contents;

	@Builder
	public BoardRequestDto(Long boardId, String contents) {
		this.boardId = boardId;
		this.contents = contents;
	}
}
