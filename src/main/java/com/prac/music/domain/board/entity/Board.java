package com.prac.music.domain.board.entity;

import java.time.LocalDateTime;

import com.prac.music.domain.board.dto.BoardRequestDto;
import com.prac.music.domain.board.dto.UpdateRequestDto;
import com.prac.music.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "board")
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "contents")
	private String contents;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	@Column(nullable = false, name = "createdAt")
	private LocalDateTime createAt;

	@Column(nullable = false, name = "updatedAt")
	private LocalDateTime updatedAt;

	public Board(BoardRequestDto requestDto, User user) {
		this.user = user;
		this.contents = requestDto.getContents();
		this.createAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public void update(UpdateRequestDto requestDto) {
		this.contents = requestDto.getContents();
		this.updatedAt = LocalDateTime.now();
	}
}