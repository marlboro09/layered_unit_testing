package com.sparta.layered_unit_testing.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.layered_unit_testing.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
