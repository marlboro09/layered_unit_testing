package com.prac.music.domain.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prac.music.domain.board.entity.Board;
import com.prac.music.domain.board.repository.BoardRepository;
import com.prac.music.domain.comment.dto.CommentRequestDto;
import com.prac.music.domain.comment.dto.CommentResponseDto;
import com.prac.music.domain.comment.dto.CommentUpdateRequestDto;
import com.prac.music.domain.comment.dto.CommentUpdateResponseDto;
import com.prac.music.domain.comment.entity.Comment;
import com.prac.music.domain.comment.repository.CommentRepository;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.repository.UserRepository;
import com.prac.music.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;

	@Autowired
	public CommentService(CommentRepository commentRepository, BoardRepository boardRepository, UserRepository userRepository) {
		this.commentRepository = commentRepository;
		this.boardRepository = boardRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public CommentResponseDto createComment(CommentRequestDto requestDto, Long boardId, User user) {
		Board findBoard = boardRepository.findById(boardId)
			.orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
		User persistentUser = userRepository.findById(user.getId())
			.orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

		Comment comment = Comment.builder()
			.contents(requestDto.getContents())
			.board(findBoard)
			.user(persistentUser)
			.build();

		try {
			Comment savedComment = commentRepository.save(comment);
			log.info("댓글 저장 : {}", savedComment);
			return new CommentResponseDto(savedComment);
		} catch (Exception e) {
			log.error("댓글 저장 중 오류가 발생하였습니다.", e);
			throw new RuntimeException("댓글 저장이 실패하였습니다.");
		}
	}

	@Transactional
	public CommentUpdateResponseDto updateComment(Long commentId, User user, CommentUpdateRequestDto requestDto) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new NotFoundException("찾으시는 댓글이 없습니다."));

		if (!validateUserAuthorization(comment, user)) {
			throw new RuntimeException("댓글 수정 권한이 없습니다.");
		}

		comment.update(requestDto.getContents());
		try {
			Comment updatedComment = commentRepository.save(comment);
			log.info("댓글 수정 : {}", updatedComment);
			return new CommentUpdateResponseDto(updatedComment);
		} catch (Exception e) {
			log.error("댓글 수정 중 오류가 발생하였습니다.", e);
			throw new RuntimeException("댓글 수정에 실패했습니다.");
		}
	}

	@Transactional
	public void deleteComment(Long commentId, User user) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new NotFoundException("찾으시는 댓글은 없습니다."));

		if (!validateUserAuthorization(comment, user)) {
			throw new RuntimeException("댓글 삭제 권한이 없습니다.");
		}

		try {
			commentRepository.delete(comment);
			log.info("댓글 삭제 : {}", comment);
		} catch (Exception e) {
			log.error("댓글 삭제 중 오류가 발생했습니다.", e);
			throw new RuntimeException("댓글 삭제에 실패했습니다.");
		}
	}

	private boolean validateUserAuthorization(Comment comment, User user) {
		User persistentUser = userRepository.findById(user.getId())
			.orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
		return comment.getUser().equals(persistentUser);
	}
}
