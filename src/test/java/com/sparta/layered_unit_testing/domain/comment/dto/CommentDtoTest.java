package com.sparta.layered_unit_testing.domain.comment.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sparta.layered_unit_testing.domain.board.entity.Board;
import com.sparta.layered_unit_testing.domain.user.entity.User;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;

class CommentDtoTest {
	private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
		.objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
		.build();

	@Test
	@DisplayName("CommentRequestDto 테스트")
	void commentRequestDto() {
		CommentRequestDto commentRequestDto = fixtureMonkey.giveMeOne(CommentRequestDto.class);

		assertThat(commentRequestDto.getContents());
	}

	@Test
	@DisplayName("CommentResponsetDto 테스트")
	void commentResponseDto() {
		CommentResponseDto commentResponseDto = fixtureMonkey.giveMeOne(CommentResponseDto.class);
		Board board = fixtureMonkey.giveMeOne(Board.class);
		User user = fixtureMonkey.giveMeOne(User.class);

		assertThat(commentResponseDto.getId());
		assertThat(commentResponseDto.getContents());
		assertThat(board.getId());
		assertThat(user.getId());
		assertThat(commentResponseDto.getUpdatedAt());
	}

	@Test
	@DisplayName("CommentUpdateRequestDto 테스트")
	void commentUpdateRequestDto() {
		CommentUpdateRequestDto commentUpdateRequestDto = fixtureMonkey.giveMeOne(CommentUpdateRequestDto.class);

		assertThat(commentUpdateRequestDto.getContents());
	}
}