package com.prac.music.domain.mail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VerifyRequestDto {
    private String email;
    private String code;
}
