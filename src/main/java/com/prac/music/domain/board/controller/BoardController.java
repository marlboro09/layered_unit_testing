package com.prac.music.domain.board.controller;

import com.prac.music.domain.board.dto.BoardRequestDto;
import com.prac.music.domain.board.dto.BoardResponseDto;
import com.prac.music.domain.board.dto.UpdateRequestDto;
import com.prac.music.domain.board.dto.UpdateResponseDto;
import com.prac.music.domain.board.service.BoardService;
import com.prac.music.domain.user.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/boards")
@Tag(name = "Board Management")
public class BoardController {

	private final BoardService boardService;

	@PostMapping
	@Operation(requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data")))
	public ResponseEntity<BoardResponseDto> createBoard(
		@RequestPart(value = "board") @Parameter(schema = @Schema(type = "string", format = "json")) BoardRequestDto requestDto,
		@RequestPart(value = "files") @ArraySchema(schema = @Schema(type = "string", format = "binary")) List<MultipartFile> files,
		@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
		BoardResponseDto responseDto = boardService.createBoard(requestDto, userDetails.getUser(), files);
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{id}")
	@Operation(requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data")))
	public ResponseEntity<UpdateResponseDto> updateBoard(
		@PathVariable("id") Long id,
		@RequestPart(value = "board") @Parameter(schema = @Schema(type = "string", format = "json")) UpdateRequestDto requestDto,
		@RequestPart(value = "files") @ArraySchema(schema = @Schema(type = "string", format = "binary")) List<MultipartFile> files,
		@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
		UpdateResponseDto responseDto = boardService.updateBoard(id, requestDto, userDetails.getUser(), files);
		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/{id}")
	@Operation
	public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		boardService.deleteBoard(id, userDetails.getUser());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/list")
	@Operation
	public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
		List<BoardResponseDto> responseDtos = boardService.getAllBoard();
		return ResponseEntity.ok(responseDtos);
	}

	@GetMapping("/{id}")
	@Operation
	public ResponseEntity<BoardResponseDto> getBoard(@PathVariable("id") Long id) {
		BoardResponseDto responseDto = boardService.getBoardById(id);
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/paging")
	@Operation(parameters = @Parameter(hidden = true))
	public ResponseEntity<Page<BoardResponseDto>> paging(
		@Parameter(hidden = true) @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<BoardResponseDto> postsPages = boardService.paging(pageable);
		return ResponseEntity.ok(postsPages);
	}
}
