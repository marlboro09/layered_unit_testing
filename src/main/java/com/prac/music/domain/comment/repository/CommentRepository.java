package com.prac.music.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prac.music.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
