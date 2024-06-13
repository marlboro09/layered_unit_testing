package com.sparta.layered_unit_testing.domain.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
	private String userId;

	private String password;
}