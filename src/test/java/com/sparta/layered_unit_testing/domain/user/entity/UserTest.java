package com.sparta.layered_unit_testing.domain.user.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sparta.layered_unit_testing.domain.user.dto.ProfileRequestDto;

import com.navercorp.fixturemonkey.FixtureMonkey;

class UserTest {

	private final FixtureMonkey fixtureMonkey = FixtureMonkey.create();

	@Test
	@DisplayName("nonPasswordProfileUpdate() 테스트 성공")
	void testNonPasswordProfileUpdate() {
		User user = fixtureMonkey.giveMeOne(User.class);
		ProfileRequestDto requestDto = fixtureMonkey.giveMeOne(ProfileRequestDto.class);
		String profileImage = fixtureMonkey.giveMeOne(String.class);

		user.nonPasswordProfileUpdate(requestDto, profileImage);

		assertEquals(requestDto.getName(), user.getName());
		assertEquals(requestDto.getEmail(), user.getEmail());
		assertEquals(requestDto.getIntro(), user.getIntro());
		assertEquals(profileImage, user.getProfileImage());
	}

	@Test
	@DisplayName("inPasswordProfileUpdate() 테스트 성공")
	void testInPasswordProfileUpdate() {
		User user = fixtureMonkey.giveMeOne(User.class);
		ProfileRequestDto requestDto = fixtureMonkey.giveMeOne(ProfileRequestDto.class);
		String encodedPasswdDto = fixtureMonkey.giveMeOne(String.class);
		String profileImage = fixtureMonkey.giveMeOne(String.class);

		user.inPasswordProfileUpdate(requestDto, encodedPasswdDto, profileImage);

		assertEquals(requestDto.getName(), user.getName());
		assertEquals(requestDto.getEmail(), user.getEmail());
		assertEquals(requestDto.getIntro(), user.getIntro());
		assertEquals(encodedPasswdDto, user.getPassword());
		assertEquals(profileImage, user.getProfileImage());
	}
}