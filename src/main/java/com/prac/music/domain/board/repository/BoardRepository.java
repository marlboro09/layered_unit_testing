package com.prac.music.domain.board.repository;

import com.prac.music.domain.board.entity.Board;
import com.prac.music.domain.user.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
	Page<Board> findAllByOrderByCreatedAtDesc(Pageable pageable);
}