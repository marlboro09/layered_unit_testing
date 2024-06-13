package com.prac.music.common.exception;

public class S3ServiceException extends RuntimeException {
	public S3ServiceException(String message) {
		super(message);
	}
}