package com.prac.music.domain.comment.entity;

import com.prac.music.domain.like.entity.ContentType;
import com.prac.music.domain.like.entity.Like;
import com.prac.music.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment_like")
@NoArgsConstructor
public class CommentLike extends Like {
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Override
    public Long getContentId() {
        return comment.getId();
    }

    public CommentLike(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
        this.contentType = ContentType.COMMENT;
    }
}
