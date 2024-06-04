package com.prac.music.domain.board.entity;

import com.prac.music.domain.board.dto.BoardRequestDto;
import com.prac.music.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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
    @JoinColumn( nullable = false,name = "user_id")
    private User user;

    @Column(nullable = false, name = "createdAt")
    private LocalDateTime createAt;

    @Column(nullable = false, name = "updatedAt")
    private LocalDateTime updatedAt;


    public Board(BoardRequestDto requestDto, User user) {
        this.id = requestDto.getBoardId();
        this.user = user;
        this.contents = requestDto.getContents();
        this.createAt = LocalDateTime.now();
    }

    public void update(BoardRequestDto requestDto) {
        this.id = requestDto.getBoardId();
        this.contents = requestDto.getContents();
        this.updatedAt = LocalDateTime.now();
    }
}
