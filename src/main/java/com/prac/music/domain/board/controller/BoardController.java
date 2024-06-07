package com.prac.music.domain.board.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import com.prac.music.domain.board.dto.UpdateResponseDto;
import com.prac.music.domain.board.service.BoardService;
import com.prac.music.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/boards")
public class BoardController {

	private final BoardService boardService;

	@PostMapping
	public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		BoardResponseDto responseDto = boardService.createBoard(requestDto, userDetails.getUser());
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UpdateResponseDto> updateBoard(@PathVariable("id") Long id, @RequestBody UpdateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		UpdateResponseDto responseDto = boardService.updateBoard(id, requestDto, userDetails.getUser());
		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		boardService.deleteBoard(id, userDetails.getUser());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/list")
	public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
		List<BoardResponseDto> responseDtos = boardService.getAllBoard();
		return ResponseEntity.ok(responseDtos);
	}

	@GetMapping("/paging")
	public ResponseEntity<Page<BoardResponseDto>> paging(
		@Parameter(hidden = true) @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<BoardResponseDto> postsPages = boardService.paging(pageable);
		return ResponseEntity.ok(postsPages);
	}
}
