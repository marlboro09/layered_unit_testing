package com.prac.music.domain.like.repository;

import com.prac.music.domain.board.entity.Board;
import com.prac.music.domain.board.entity.BoardLike;
import com.prac.music.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardAndUser(Board board, User getUser);

    int countLikesByBoardId(Long boardId);
}
