package com.prac.music.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateRequestDto {
	private String contents;

	@Builder
	public UpdateRequestDto(String contents) {
		this.contents = contents;
	}
}
