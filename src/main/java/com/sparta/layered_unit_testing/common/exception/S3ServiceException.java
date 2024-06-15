package com.sparta.layered_unit_testing.common.exception;

public class S3ServiceException extends RuntimeException {
	public S3ServiceException(String message) {
		super(message);
	}
}