package com.prac.music.domain.like.controller;

import com.prac.music.domain.like.service.LikeService;
import com.prac.music.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    public void boardLike(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

    }

    @PostMapping("/comments/{commentId}/like")
    public void commentLike(@PathVariable Long boardId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

    }
}
