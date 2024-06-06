package com.prac.music.domain.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.prac.music.domain.board.entity.Board;
import com.prac.music.domain.comment.dto.CommentRequestDto;
import com.prac.music.domain.comment.dto.CommentResponseDto;
import com.prac.music.domain.comment.dto.CommentUpdateRequestDto;
import com.prac.music.domain.comment.dto.CommentUpdateResponseDto;
import com.prac.music.domain.comment.service.CommentService;
import com.prac.music.domain.user.entity.User;
import com.prac.music.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping
	public ResponseEntity<CommentResponseDto> createComment(@PathVariable("boardId") Long boardId,
		@RequestBody CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentResponseDto responseDto = commentService.createComment(requestDto, boardId, userDetails.getUser());
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<CommentUpdateResponseDto> updateComment(@PathVariable("commentId") Long commentId,
		@RequestBody CommentUpdateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentUpdateResponseDto responseDto = commentService.updateComment(commentId, userDetails.getUser(), requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		commentService.deleteComment(commentId, userDetails.getUser());
		return ResponseEntity.noContent().build();
	}
}
