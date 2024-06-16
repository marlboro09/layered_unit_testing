package com.sparta.layered_unit_testing.domain.user.dto;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    }
}