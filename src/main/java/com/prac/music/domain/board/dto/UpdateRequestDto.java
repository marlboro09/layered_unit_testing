package com.prac.music.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestDto {
	private Long boardId;

	private String contents;
}
