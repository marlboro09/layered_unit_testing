package com.sparta.layered_unit_testing.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.layered_unit_testing.domain.board.entity.BoardFiles;

@Repository
public interface BoardFilesRepository extends JpaRepository<BoardFiles, Long> {
}
