package com.sparta.layered_unit_testing.domain.board.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sparta.layered_unit_testing.domain.user.entity.User;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;

class BoardTest {
	private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
		.objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
		.build();

	@Test
	@DisplayName("Board 테스트")
	void boardCreateTest() {
		Board board = fixtureMonkey.giveMeOne(Board.class);
		User user = fixtureMonkey.giveMeOne(User.class);

		assertThat(board.getTitle());
		assertThat(board.getContents());
		assertThat(user.getUserId());
	}

	@Test
	@DisplayName("Update 테스트")
	void boardUpdateTest() {
		Board board = new Board();
		Board board2 = fixtureMonkey.giveMeOne(Board.class);

		board.update(board2.getTitle(), board2.getContents());

		assertEquals(board.getTitle(), board2.getTitle());
		assertEquals(board.getContents(), board2.getContents());
	}
}