package com.prac.music.domain.like.repository;

import com.prac.music.domain.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    int countLikesByBoardId(Long boardId);
}
