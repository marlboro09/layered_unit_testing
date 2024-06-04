package com.prac.music.domain.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prac.music.domain.board.dto.BoardRequestDto;
import com.prac.music.domain.board.dto.BoardResponseDto;
import com.prac.music.domain.board.dto.UpdateRequestDto;
import com.prac.music.domain.board.entity.Board;
import com.prac.music.domain.board.repository.BoardRepository;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.repsitory.UserRepository;
import com.prac.music.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;

	public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
		this.boardRepository = boardRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
		User persistentUser = userRepository.findById(user.getId())
			.orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

		Board board = new Board(requestDto, persistentUser);
		Board savedBoard = boardRepository.save(board);
		log.info("게시물 저장: {}", savedBoard);

		return new BoardResponseDto(savedBoard);
	}

	@Transactional
	public BoardResponseDto updateBoard(Long id, UpdateRequestDto requestDto, User user) {
		Board board = boardRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));

		if (isNotAuthorizedUser(board, user)) {
			throw new IllegalStateException("권한이 없습니다.");
		}

		board.update(requestDto);
		Board updatedBoard = boardRepository.save(board);
		log.info("게시글 수정: {}", updatedBoard);
		return new BoardResponseDto(updatedBoard);
	}

	@Transactional
	public void deleteBoard(Long id, User user) {
		Board board = boardRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));

		if (isNotAuthorizedUser(board, user)) {
			throw new IllegalStateException("권한이 없습니다.");
		}

		boardRepository.delete(board);
		log.info("게시글 삭제: {}", board);
	}

	@Transactional(readOnly = true)
	public List<BoardResponseDto> getAllBoard() {
		return boardRepository.findAll().stream()
			.map(BoardResponseDto::new)
			.collect(Collectors.toList());
	}

	private boolean isNotAuthorizedUser(Board board, User user) {
		User persistentUser = userRepository.findById(user.getId())
			.orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
		return !board.getUser().equals(persistentUser) && !persistentUser.isAdmin();
	}
}