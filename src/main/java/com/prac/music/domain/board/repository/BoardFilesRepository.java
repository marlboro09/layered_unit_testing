package com.prac.music.domain.board.repository;

import com.prac.music.domain.board.entity.BoardFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardFilesRepository extends JpaRepository<BoardFiles, Long> {
}
