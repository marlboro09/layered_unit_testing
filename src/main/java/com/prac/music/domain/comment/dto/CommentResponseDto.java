package com.prac.music.domain.comment.dto;

import com.prac.music.domain.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;

    private String contents;

    private Long boardId;

    private Long userId;

    private LocalDateTime updatedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.boardId = comment.getBoard().getId();
        this.userId = comment.getUser().getId();
        this.updatedAt = comment.getCreatedAt();
    }
}
