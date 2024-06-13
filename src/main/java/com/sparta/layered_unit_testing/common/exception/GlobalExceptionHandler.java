package com.sparta.layered_unit_testing.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(com.sparta.layered_unit_testing.common.exception.UserServiceException.class)
	public ResponseEntity<String> handleUserServiceException(com.sparta.layered_unit_testing.common.exception.UserServiceException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(com.sparta.layered_unit_testing.common.exception.MailServiceException.class)
	public ResponseEntity<String> handleMailServiceException(com.sparta.layered_unit_testing.common.exception.MailServiceException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}

	@ExceptionHandler(com.sparta.layered_unit_testing.common.exception.ProfileServiceException.class)
	public ResponseEntity<String> handleProfileServiceException(com.sparta.layered_unit_testing.common.exception.ProfileServiceException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(com.sparta.layered_unit_testing.common.exception.JwtServiceException.class)
	public ResponseEntity<String> handleJwtServiceException(com.sparta.layered_unit_testing.common.exception.JwtServiceException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	@ExceptionHandler(com.sparta.layered_unit_testing.common.exception.LikeServiceException.class)
	public ResponseEntity<String> handleLikeServiceException(com.sparta.layered_unit_testing.common.exception.LikeServiceException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(com.sparta.layered_unit_testing.common.exception.S3ServiceException.class)
	public ResponseEntity<String> handleS3ServiceException(com.sparta.layered_unit_testing.common.exception.S3ServiceException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다: " + e.getMessage());
	}
}
