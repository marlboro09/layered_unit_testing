package com.prac.music.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardRequestDto {

	private String contents;

	@Builder
	public BoardRequestDto(String contents) {
		this.contents = contents;
	}
}
