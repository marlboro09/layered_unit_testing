package com.sparta.layered_unit_testing.domain.user.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sparta.layered_unit_testing.domain.user.entity.User;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;

public class UserTest {
	private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
		.objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
		.build();

	@Test
	@DisplayName("updateProfile() 테스트")
	void test1() {
		// given
		User user = new User();
		String name = fixtureMonkey.giveMeOne(String.class);
		String introduction = fixtureMonkey.giveMeOne(String.class);
		String password = fixtureMonkey.giveMeOne(String.class);

		// when
		user.nonPasswordProfileUpdate(name, introduction, password);

		// then
		assertEquals(name, user.getName());
		assertEquals(introduction, user.getIntro());
		assertEquals(password, user.getPassword());
	}
}
