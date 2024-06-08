package com.prac.music.domain.like.service;

import com.prac.music.domain.board.repository.BoardRepository;
import com.prac.music.domain.comment.repository.CommentRepository;
import com.prac.music.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 본인이 작성한 게시물과 댓글에는 좋아요를 남길 수 없다
    // 같은 게시물에는 사용자당 한번만 좋아요 가능 / 두번 누르면 좋아요 취소

}
