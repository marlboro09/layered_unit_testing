package com.sparta.layered_unit_testing.domain.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.layered_unit_testing.common.exception.CommentServiceException;
import com.sparta.layered_unit_testing.common.exception.UnauthorizedAccessException;
import com.sparta.layered_unit_testing.domain.board.entity.Board;
import com.sparta.layered_unit_testing.domain.board.repository.BoardRepository;
import com.sparta.layered_unit_testing.domain.comment.dto.CommentRequestDto;
import com.sparta.layered_unit_testing.domain.comment.dto.CommentResponseDto;
import com.sparta.layered_unit_testing.domain.comment.dto.CommentUpdateRequestDto;
import com.sparta.layered_unit_testing.domain.comment.entity.Comment;
import com.sparta.layered_unit_testing.domain.comment.repository.CommentRepository;
import com.sparta.layered_unit_testing.domain.user.entity.User;
import com.sparta.layered_unit_testing.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;

	@Transactional
	public CommentResponseDto createComment(CommentRequestDto requestDto,
		Long boardId,
		User user) {
		Board findBoard = findBoardById(boardId);
		User persistentUser = findUserById(user.getId());

		Comment comment = Comment.builder()
			.contents(requestDto.getContents())
			.board(findBoard)
			.user(persistentUser)
			.build();

		Comment savedComment = commentRepository.save(comment);
		return new CommentResponseDto(savedComment);
	}

	@Transactional
	public CommentResponseDto updateComment(Long commentId,
		User user,
		CommentUpdateRequestDto requestDto) {
		Comment comment = findCommentById(commentId);
		User persistentUser = findUserById(user.getId());

		validateUserAuthorization(comment, persistentUser);

		comment.update(requestDto.getContents());
		Comment updatedComment = commentRepository.save(comment);
		return new CommentResponseDto(updatedComment);
	}

	@Transactional
	public void deleteComment(Long commentId,
		User user) {
		Comment comment = findCommentById(commentId);
		User persistentUser = findUserById(user.getId());

		validateUserAuthorization(comment, persistentUser);

		commentRepository.delete(comment);
	}

	public Comment findCommentById(Long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentServiceException("찾으시는 댓글이 없습니다."));
	}

	private User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new CommentServiceException("사용자를 찾을 수 없습니다."));
	}

	private Board findBoardById(Long boardId) {
		return boardRepository.findById(boardId)
			.orElseThrow(() -> new CommentServiceException("게시글을 찾을 수 없습니다."));
	}

	private void validateUserAuthorization(Comment comment,
		User user) {
		if (!comment.getUser().equals(user)) {
			throw new UnauthorizedAccessException("권한이 없습니다.");
		}
	}
}
