package com.sparta.layered_unit_testing.domain.like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.layered_unit_testing.domain.like.service.LikeService;
import com.sparta.layered_unit_testing.domain.user.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}")
public class LikeController {

	private final LikeService likeService;

	@PostMapping("/like")
	public ResponseEntity<String> boardLike(@PathVariable Long boardId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		int boardAllLike = likeService.boardLike(boardId, userDetails.getUser());
		return ResponseEntity.ok("해당 게시글의 전체 좋아요 : " + boardAllLike);
	}

	@PostMapping("/comments/{commentId}/like")
	public ResponseEntity<String> commentLike(@PathVariable Long boardId,
		@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		int commentAllLike = likeService.commentLike(boardId, commentId, userDetails.getUser());
		return ResponseEntity.ok("해당 댓글의 전체 좋아요 : " + commentAllLike);
	}
}
