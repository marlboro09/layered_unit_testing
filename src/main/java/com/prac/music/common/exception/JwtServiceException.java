package com.prac.music.common.exception;

public class JwtServiceException extends RuntimeException {
    public JwtServiceException(String message) {
        super(message);
    }
}
