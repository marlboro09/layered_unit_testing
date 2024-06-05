package com.prac.music.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDto {

	private String contents;

	@Builder
	public BoardRequestDto(String contents) {
		this.contents = contents;
	}
}
