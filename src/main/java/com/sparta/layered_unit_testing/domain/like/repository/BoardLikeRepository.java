package com.sparta.layered_unit_testing.domain.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.layered_unit_testing.domain.board.entity.Board;
import com.sparta.layered_unit_testing.domain.board.entity.BoardLike;
import com.sparta.layered_unit_testing.domain.user.entity.User;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
	Optional<BoardLike> findByBoardAndUser(Board board, User getUser);

	int countLikesByBoardId(Long boardId);
}
