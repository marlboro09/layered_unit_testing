package com.prac.music.domain.board.entity;

import com.prac.music.domain.like.entity.ContentType;
import com.prac.music.domain.like.entity.Like;
import com.prac.music.domain.like.entity.Sharer;
import com.prac.music.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board_like")
@NoArgsConstructor
public class BoardLike extends Like implements Sharer {
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Override
    public Long getContentId() {
        return board.getId();
    }

    @Override
    public User getUser() {
        return user;
    }

    public BoardLike(Board board, User user) {
        this.board = board;
        this.user = user;
        this.contentType = ContentType.BOARD;
    }
}
