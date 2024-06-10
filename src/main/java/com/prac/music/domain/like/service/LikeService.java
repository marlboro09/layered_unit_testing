package com.prac.music.domain.like.service;

import com.prac.music.common.exception.LikeServiceException;
import com.prac.music.domain.board.entity.Board;
import com.prac.music.domain.board.entity.BoardLike;
import com.prac.music.domain.board.service.BoardService;
import com.prac.music.domain.comment.entity.Comment;
import com.prac.music.domain.comment.entity.CommentLike;
import com.prac.music.domain.comment.service.CommentService;
import com.prac.music.domain.like.entity.Sharer;
import com.prac.music.domain.like.repository.BoardLikeRepository;
import com.prac.music.domain.like.repository.CommentLikeRepository;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final BoardService boardService;
    private final CommentService commentService;
    private final ProfileService profileService;

    @Transactional
    public int boardLike(Long boardId,
                         User user) {
        Board board = boardService.findBoardById(boardId);
        User getUser = profileService.findUserById(user.getUserId());
        BoardLike boardLike = new BoardLike(board, getUser);
        checkIfUserLike(boardLike, getUser);

        Optional<BoardLike> existingLike = boardLikeRepository.findByBoardAndUser(board, getUser);
        if (existingLike.isPresent()) {
            // 이미 좋아요를 누른 상태라면 취소
            boardLikeRepository.delete(existingLike.get());
        } else {
            // 좋아요 추가
            boardLikeRepository.save(boardLike);
        }
        return boardLikeRepository.countLikesByBoardId(boardId);
    }

    @Transactional
    public int commentLike(Long boardId,
                           Long commentId,
                           User user) {
        boardService.findBoardById(boardId);
        User getUser = profileService.findUserById(user.getUserId());
        Comment comment = commentService.findCommentById(commentId);
        CommentLike commentLike = new CommentLike(comment, getUser);
        checkIfUserLike(commentLike, getUser);

        Optional<CommentLike> existingLike = commentLikeRepository.findByCommentAndUser(comment, getUser);
        if (existingLike.isPresent()) {
            // 이미 좋아요를 누른 상태라면 취소
            commentLikeRepository.delete(existingLike.get());
        } else {
            // 좋아요 추가
            commentLikeRepository.save(commentLike);
        }
        return commentLikeRepository.countLikesByCommentId(commentId);
    }

    private void checkIfUserLike(Sharer sharer, User user) {
        if (sharer.getUser().getId().equals(user.getId())) {
            throw new LikeServiceException("자신이 작성한 컨텐츠에는 좋아요를 남길 수 없습니다.");
        }
    }
}
