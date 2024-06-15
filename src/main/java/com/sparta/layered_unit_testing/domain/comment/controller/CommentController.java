package com.sparta.layered_unit_testing.domain.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.layered_unit_testing.domain.comment.dto.CommentRequestDto;
import com.sparta.layered_unit_testing.domain.comment.dto.CommentResponseDto;
import com.sparta.layered_unit_testing.domain.comment.dto.CommentUpdateRequestDto;
import com.sparta.layered_unit_testing.domain.comment.service.CommentService;
import com.sparta.layered_unit_testing.domain.user.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<CommentResponseDto> createComment(@PathVariable("boardId") Long boardId,
		@RequestBody CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentResponseDto responseDto = commentService.createComment(requestDto, boardId, userDetails.getUser());
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<CommentResponseDto> updateComment(@PathVariable("boardId") Long boardId,
		@PathVariable("commentId") Long commentId,
		@RequestBody CommentUpdateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentResponseDto responseDto = commentService.updateComment(commentId, userDetails.getUser(), requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable("boardId") Long boardId,
		@PathVariable("commentId") Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		commentService.deleteComment(commentId, userDetails.getUser());
		return ResponseEntity.noContent().build();
	}
}
