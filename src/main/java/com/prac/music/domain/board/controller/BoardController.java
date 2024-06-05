package com.prac.music.domain.board.controller;

import java.util.List;
import java.util.Map;

import com.prac.music.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prac.music.domain.board.dto.BoardRequestDto;
import com.prac.music.domain.board.dto.BoardResponseDto;
import com.prac.music.domain.board.dto.UpdateRequestDto;
import com.prac.music.domain.board.service.BoardService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/boards")
@Slf4j
public class BoardController {

	private final BoardService boardService;

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@PostMapping
	public ResponseEntity<?> createBoard(@Valid @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			BoardResponseDto responseDto = boardService.createBoard(requestDto, userDetails.getUser());
			return ResponseEntity.ok(responseDto);
		} catch (Exception e) {
			log.error("게시물 생성 중 오류 발생", e);
			return ResponseEntity.status(500).body(Map.of("error", "게시물 생성 중 오류가 발생했습니다."));
		}
	}

	@GetMapping("/list")
	public ResponseEntity<?> getAllBoards() {
		try {
			List<BoardResponseDto> responseDtos = boardService.getAllBoard();
			return ResponseEntity.ok(responseDtos);
		} catch (Exception e) {
			log.error("게시물 조회 중 오류 발생", e);
			return ResponseEntity.status(500).body(Map.of("error", "게시물 조회 중 오류가 발생했습니다."));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateBoard(@PathVariable("id") Long id, @Valid @RequestBody UpdateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			BoardResponseDto responseDto = boardService.updateBoard(id, requestDto, userDetails.getUser());
			return ResponseEntity.ok(responseDto);
		} catch (Exception e) {
			log.error("게시물 수정 중 오류 발생", e);
			return ResponseEntity.status(500).body(Map.of("error", "게시물 수정 중 오류가 발생했습니다."));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBoard(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			boardService.deleteBoard(id, userDetails.getUser());
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			log.error("게시물 삭제 중 오류 발생", e);
			return ResponseEntity.status(500).body(Map.of("error", "게시물 삭제 중 오류가 발생했습니다."));
		}
	}
}
