package com.prac.music.domain.comment.controller;

import com.prac.music.domain.comment.dto.CommentRequestDto;
import com.prac.music.domain.comment.dto.CommentResponseDto;
import com.prac.music.domain.comment.dto.CommentUpdateRequestDto;
import com.prac.music.domain.comment.service.CommentService;
import com.prac.music.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
