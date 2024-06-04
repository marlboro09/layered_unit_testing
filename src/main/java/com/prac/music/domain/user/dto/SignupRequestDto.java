package com.prac.music.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @Size(min = 10, max = 20)
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String userId;

    private String password;

    private String name;

    private String email;

    private String intro;

}
