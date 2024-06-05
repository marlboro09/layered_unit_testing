package com.prac.music.domain.board.entity;

import java.time.LocalDateTime;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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

	@Builder
	public Board(String contents, User user, LocalDateTime createAt, LocalDateTime updatedAt) {
		this.contents = contents;
		this.user = user;
		this.createAt = createAt;
		this.updatedAt = updatedAt;
	}

	public void update(UpdateRequestDto requestDto) {
		this.contents = requestDto.getContents();
		this.updatedAt = LocalDateTime.now();
	}
}
