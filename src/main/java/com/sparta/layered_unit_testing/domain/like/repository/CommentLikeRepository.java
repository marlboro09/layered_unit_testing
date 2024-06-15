package com.sparta.layered_unit_testing.domain.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.layered_unit_testing.domain.comment.entity.Comment;
import com.sparta.layered_unit_testing.domain.comment.entity.CommentLike;
import com.sparta.layered_unit_testing.domain.user.entity.User;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
	Optional<CommentLike> findByCommentAndUser(Comment comment, User getUser);

	int countLikesByCommentId(Long commentId);
}
