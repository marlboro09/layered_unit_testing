package com.sparta.layered_unit_testing.domain.board.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;

class BoardDtoTest {

	private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
		.objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
		.build();

	@Test
	@DisplayName("BoardRequest 테스트")
	void boardRequestDto() {
		BoardRequestDto boardRequestDto = fixtureMonkey.giveMeOne(BoardRequestDto.class);

		assertThat(boardRequestDto.getTitle());
		assertThat(boardRequestDto.getContents());

	}

	@Test
	@DisplayName("BoardResponse 테스트")
	void boardResponseDto() {
		BoardResponseDto boardResponseDto = fixtureMonkey.giveMeOne(BoardResponseDto.class);

		assertThat(boardResponseDto.getBoardId());
		assertThat(boardResponseDto.getUserId());
		assertThat(boardResponseDto.getTitle());
		assertThat(boardResponseDto.getContents());
		assertThat(boardResponseDto.getUpdatedAt());
	}

	@Test
	@DisplayName("UpdateRequest 테스트")
	void boardUpdateRequestDto() {
		UpdateRequestDto updateRequestDto = fixtureMonkey.giveMeOne(UpdateRequestDto.class);

		assertThat(updateRequestDto.getTitle());
		assertThat(updateRequestDto.getContents());
	}
}