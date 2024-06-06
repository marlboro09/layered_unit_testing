package com.prac.music.domain.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prac.music.domain.comment.dto.CommentRequestDto;
import com.prac.music.domain.comment.dto.CommentResponseDto;
import com.prac.music.domain.comment.service.CommentService;
import com.prac.music.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/boards/{id}/comments")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping
	public ResponseEntity<CommentResponseDto> createComment(@PathVariable("BoardId") Long BoardId, @RequestBody CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentResponseDto responseDto = commentService.createComment(requestDto, userDetails.getUser());
		return ResponseEntity.ok(responseDto);
	}
}
