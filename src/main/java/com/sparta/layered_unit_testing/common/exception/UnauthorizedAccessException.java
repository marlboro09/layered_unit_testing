package com.sparta.layered_unit_testing.common.exception;

public class UnauthorizedAccessException extends RuntimeException {
	public UnauthorizedAccessException(String message) {
		super(message);
	}
}
