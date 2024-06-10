package com.prac.music.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저를 찾을 수 없습니다: " + e.getMessage());
	}

	@ExceptionHandler(BoardRuntimeException.class)
	public ResponseEntity<String> handleBoardRuntimeException(BoardRuntimeException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 처리 중 오류가 발생했습니다: " + e.getMessage());
	}

	@ExceptionHandler(CommentRuntimeException.class)
	public ResponseEntity<String> handleCommentRuntimeException(CommentRuntimeException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 처리 중 오류가 발생했습니다: " + e.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}
