package com.sparta.layered_unit_testing.domain.board.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardRequestDtoTest {

	@Test
	@DisplayName("게시판 빌드 테스트")
	void testBoardRequestDtoBuilder() {

		//given
		String title = "Test Title";
		String contents = "Test Contents";

		//when
		BoardRequestDto requestDto = BoardRequestDto.builder()
			.title(title)
			.contents(contents)
			.build();

		//then
		assertThat(requestDto.getTitle()).isEqualTo(title);
		assertThat(requestDto.getContents()).isEqualTo(contents);
	}

	@Test
	@DisplayName("공백 입력 테스트")
	void testNoArgsConstructor() {

		//given
	String title = null;
	String contents = null;

	//when
	BoardRequestDto requestDto = BoardRequestDto.builder()
		.title(title)
		.contents(contents)
		.build();

	//then
	assertThat(requestDto.getTitle()).isEqualTo(title);
	assertThat(requestDto.getContents()).isEqualTo(contents);
}