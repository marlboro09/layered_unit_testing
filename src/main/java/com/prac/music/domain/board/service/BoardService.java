package com.prac.music.domain.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prac.music.domain.board.dto.BoardRequestDto;
import com.prac.music.domain.board.dto.BoardResponseDto;
import com.prac.music.domain.board.dto.UpdateRequestDto;
import com.prac.music.domain.board.dto.UpdateResponseDto;
import com.prac.music.domain.board.entity.Board;
import com.prac.music.domain.board.repository.BoardRepository;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.repository.UserRepository;
import com.prac.music.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final UserRepository userRepository;

	@Autowired
	public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
		this.boardRepository = boardRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
		User persistentUser = userRepository.findById(user.getId())
			.orElseThrow(() -> new NotFoundException("사용자 ID " + user.getId() + "를 찾을 수 없습니다."));

		Board board = Board.builder()
			.title(requestDto.getTitle())
			.contents(requestDto.getContents())
			.user(persistentUser)
			.build();

		Board savedBoard = boardRepository.save(board);
		log.info("게시물 저장: {}", savedBoard);

		return new BoardResponseDto(savedBoard);
	}

	@Transactional
	public UpdateResponseDto updateBoard(Long id, UpdateRequestDto requestDto, User user) {
		Board board = boardRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("게시물 ID " + id + "를 찾을 수 없습니다."));

		validateUserAuthorization(board, user);

		board.update(requestDto.getTitle());
		board.update(requestDto.getContents());
		Board updatedBoard = boardRepository.save(board);
		log.info("게시글 수정 성공: {}", updatedBoard);

		return new UpdateResponseDto(updatedBoard);
	}

	@Transactional
	public void deleteBoard(Long id, User user) {
		Board board = boardRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("게시물 ID " + id + "를 찾을 수 없습니다."));

		validateUserAuthorization(board, user);

		boardRepository.delete(board);
		log.info("게시글 삭제: {}", board);
	}

	@Transactional(readOnly = true)
	public List<BoardResponseDto> getAllBoard() {
		return boardRepository.findAll().stream()
			.map(BoardResponseDto::new)
			.collect(Collectors.toList());
	}

	private void validateUserAuthorization(Board board, User user) {
		if (!isAuthorizedUser(board, user)) {
			log.warn("사용자 {}는 게시글 {}의 수정 권한이 없습니다.", user.getId(), board.getId());
			throw new IllegalStateException("수정 권한이 없습니다.");
		}
	}

	private boolean isAuthorizedUser(Board board, User user) {
		return board.getUser().equals(user);
	}
}
