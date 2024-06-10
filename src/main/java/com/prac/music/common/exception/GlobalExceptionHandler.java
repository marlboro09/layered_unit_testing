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

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<String> handleBoardNotFoundException(BoardNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시물을 찾을 수 없습니다: " + e.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleCommentNotFoundException(CommentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글을 찾을 수 없습니다: " + e.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다: " + e.getMessage());
    }

    @ExceptionHandler(BoardRuntimeException.class)
    public ResponseEntity<String> handleBoardRuntimeException(BoardRuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 처리 중 오류가 발생했습니다: " + e.getMessage());
    }

    @ExceptionHandler(CommentRuntimeException.class)
    public ResponseEntity<String> handleCommentRuntimeException(CommentRuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 처리 중 오류가 발생했습니다: " + e.getMessage());
    }

    @ExceptionHandler(PasswordRuntimeException.class)
    public ResponseEntity<String> handlePasswordRuntimeException(PasswordRuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 오류: " + e.getMessage());
    }
}
