package com.prac.music.domain.like.repository;

import com.prac.music.domain.comment.entity.Comment;
import com.prac.music.domain.comment.entity.CommentLike;
import com.prac.music.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentAndUser(Comment comment, User getUser);
    int countLikesByCommentId(Long commentId);
}
