package com.prac.music.domain.board.entity;

import com.prac.music.domain.user.entity.BaseTimeEntity;
import com.prac.music.domain.user.entity.User;

import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자는 protected로 설정합니다.
@Entity
@Table(name = "board")
public class Board extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "title")
	private String title;

	@Column(nullable = false, name = "contents")
	private String contents;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	@Builder
	public Board(String title, String contents, User user) {
		this.title = title;
		this.contents = contents;
		this.user = user;
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
}
