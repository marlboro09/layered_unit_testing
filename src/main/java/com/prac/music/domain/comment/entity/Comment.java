package com.prac.music.domain.comment.entity;

import java.time.LocalDateTime;
import java.util.Collections;

import com.prac.music.domain.board.entity.Board;
import com.prac.music.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "comment")
@NoArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String contents;

	@Column
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "board_id", nullable = false)
	private Board board;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
    private User user;

	@Builder
	public Comment(String contents, Board board, User user) {
		this.contents = contents;
        this.board = board;
        this.user = user;
        this.createdAt = LocalDateTime.now();
	}

	public void update(String contents) {
        this.contents = contents;

    }
}
