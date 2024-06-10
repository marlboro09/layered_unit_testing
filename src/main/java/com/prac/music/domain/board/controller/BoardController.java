package com.prac.music.domain.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prac.music.domain.board.dto.BoardRequestDto;
import com.prac.music.domain.board.dto.BoardResponseDto;
import com.prac.music.domain.board.dto.UpdateRequestDto;
import com.prac.music.domain.board.service.BoardService;
import com.prac.music.domain.user.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/boards")
public class BoardController {

	private final BoardService boardService;

	@PostMapping
	public ResponseEntity<BoardResponseDto> createBoard(@RequestPart(value = "files", required = false) List<MultipartFile> files,
		@RequestPart(value = "board") BoardRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
		BoardResponseDto responseDto = boardService.createBoard(requestDto, userDetails.getUser(), files);
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable("id") Long id,
		@RequestPart(value = "files", required = false) List<MultipartFile> files,
		@RequestPart(value = "board") UpdateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
		BoardResponseDto responseDto = boardService.updateBoard(id, requestDto, userDetails.getUser(), files);
		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		boardService.deleteBoard(id, userDetails.getUser());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/list")
	public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
		List<BoardResponseDto> responseDtos = boardService.getAllBoard();
		return ResponseEntity.ok(responseDtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BoardResponseDto> getBoard(@PathVariable(value = "boardId") Long id) {
		BoardResponseDto responseDto = boardService.getBoardById(id);
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/boards")
	public ResponseEntity<Page<BoardResponseDto>> getBoards(
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "size", defaultValue = "10") int size,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") Sort.Direction direction) {

		// 페이지 번호는 1부터 시작하지만, Spring Data JPA는 0부터 시작하므로 1을 빼줍니다.
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sort));
		Page<BoardResponseDto> postsPages = boardService.paging(pageable);
		return ResponseEntity.ok(postsPages);
	}
}
