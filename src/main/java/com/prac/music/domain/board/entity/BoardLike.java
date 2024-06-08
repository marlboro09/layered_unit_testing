package com.prac.music.domain.board.entity;

import com.prac.music.domain.like.entity.Like;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "board_like")
public class BoardLike extends Like {
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Override
    public Long getContentId() {
        return board.getId();
    }
}
