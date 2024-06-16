package com.sparta.layered_unit_testing.domain.user.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sparta.layered_unit_testing.domain.user.entity.User;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;

class UserDtoTest {
	private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
		.objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
		.build();

	@Test
	@DisplayName("LoginRequestDto 테스트")
	void loginRequestDto() {
		LoginRequestDto loginRequestDto = fixtureMonkey.giveMeOne(LoginRequestDto.class);

		assertThat(loginRequestDto.getUserId());
		assertThat(loginRequestDto.getPassword());
	}

	@Test
	@DisplayName("LoginResponseDto 테스트")
	void loginResponseDto() {
		LoginResponseDto loginResponseDto = fixtureMonkey.giveMeOne(LoginResponseDto.class);

		assertThat(loginResponseDto.getToken());
		assertThat(loginResponseDto.getMessage());
		assertThat(loginResponseDto.getRefreshToken());
	}

	@Test
	@DisplayName("ProfileRequestDto 테스트")
	void profileRequestDto() {
		ProfileRequestDto profileRequestDto = fixtureMonkey.giveMeOne(ProfileRequestDto.class);

		assertThat(profileRequestDto.getName());
		assertThat(profileRequestDto.getEmail());
		assertThat(profileRequestDto.getIntro());
		assertThat(profileRequestDto.getPassword());
		assertThat(profileRequestDto.getNewPassword());
	}

	@Test
	@DisplayName("ProfileResponse 테스트")
	void profileResponseDto() {
		ProfileResponseDto profileResponseDto = fixtureMonkey.giveMeOne(ProfileResponseDto.class);

		assertThat(profileResponseDto.getUserId());
		assertThat(profileResponseDto.getName());
		assertThat(profileResponseDto.getEmail());
		assertThat(profileResponseDto.getIntro());
	}

	@Test
	@DisplayName("SignupRequestDto 테스트")
	void signupRequestDto() {
		SignupRequestDto signupRequestDto = fixtureMonkey.giveMeOne(SignupRequestDto.class);

		assertThat(signupRequestDto.getUserId());
		assertThat(signupRequestDto.getPassword());
		assertThat(signupRequestDto.getName());
		assertThat(signupRequestDto.getEmail());
		assertThat(signupRequestDto.getIntro());
	}

	@Test
	@DisplayName("SignoutRequestDto 테스트")
	void signoutRequestDto() {
		SignoutRequestDto signoutRequestDto = fixtureMonkey.giveMeOne(SignoutRequestDto.class);

		assertThat(signoutRequestDto.getPassword());
	}
}