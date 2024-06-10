package com.prac.music.domain.mail.entity;

import com.prac.music.domain.user.entity.BaseTimeEntity;
import com.prac.music.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "mail")
public class Mail extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private boolean status;

    @Column
    private String email;

    @Column
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Mail(User user) {
        this.user = user;
        this.email = user.getEmail();
        this.code = "";
        this.status = false;
    }

    public void mailAddCode(String code) {
        this.code = code;
    }
}
