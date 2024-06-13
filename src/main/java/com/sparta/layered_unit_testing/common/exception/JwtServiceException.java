package com.sparta.layered_unit_testing.common.exception;

public class JwtServiceException extends RuntimeException {
	public JwtServiceException(String message) {
		super(message);
	}
}
