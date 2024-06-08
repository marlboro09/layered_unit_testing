package com.prac.music.domain.like.repository;

import com.prac.music.domain.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    int countLikesByCommentId(Long commentId);
}
