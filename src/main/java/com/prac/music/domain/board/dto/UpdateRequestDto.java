package com.prac.music.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateRequestDto {

	private String contents;

	@Builder
	public UpdateRequestDto(String contents) {
		this.contents = contents;
	}
}
