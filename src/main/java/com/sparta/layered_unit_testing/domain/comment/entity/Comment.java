package com.sparta.layered_unit_testing.domain.comment.entity;

import com.sparta.layered_unit_testing.domain.board.entity.Board;
import com.sparta.layered_unit_testing.domain.user.entity.BaseTimeEntity;
import com.sparta.layered_unit_testing.domain.user.entity.User;

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
public class Comment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String contents;

	@ManyToOne
	@JoinColumn(name = "board_id", nullable = false)
	private Board board;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Builder
	public Comment(String contents, User user, Board board) {
		this.contents = contents;
		this.user = user;
		this.board = board;
	}

	public void update(String contents) {
		this.contents = contents;
	}
}
