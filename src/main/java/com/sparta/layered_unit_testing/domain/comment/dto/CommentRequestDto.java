package com.sparta.layered_unit_testing.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
	private String contents;

	@Builder
	public CommentRequestDto(String contents) {
		this.contents = contents;
	}
}
