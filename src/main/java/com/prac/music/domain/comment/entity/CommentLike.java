package com.prac.music.domain.comment.entity;

import com.prac.music.domain.like.entity.Like;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comment_like")
public class CommentLike extends Like {
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Override
    public Long getContentId() {
        return comment.getId();
    }
}
