package com.prac.music.domain.board.entity;

import com.prac.music.domain.user.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "board_files")
public class BoardFiles extends BaseTimeEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String file;

    @ManyToOne (fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;
}
