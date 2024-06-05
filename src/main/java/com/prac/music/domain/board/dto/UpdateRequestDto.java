package com.prac.music.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateRequestDto {

	private Long boardId;

	private String contents;

	@Builder
	public UpdateRequestDto(Long boardId, String contents) {
		this.boardId = boardId;
		this.contents = contents;
	}
}
