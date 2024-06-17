package com.sparta.layered_unit_testing.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {
	@NotBlank(message = "이름 입력은 필수입니다.")
	private String name;  // 이름

	@NotBlank(message = "이메일 입력은 필수입니다.")
	@Email(message = "유효한 이메일 주소를 입력해주세요.")
	private String email;  // 이메일

	@NotBlank(message = "소개글 입력은 필수입니다.")
	private String intro;  // 한줄소개

	@Size(min = 10, message = "비밀번호는 최소 10자 이상 입니다.")
	@Pattern(
		regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{10,}$",
		message = "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다."
	)
	private String password;  // 비밀번호

	@Size(min = 10, message = "비밀번호는 최소 10자 이상 입니다.")
	@Pattern(
		regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{10,}$",
		message = "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다."
	)
	private String newPassword; // 새 비밀번호 (선택사항)

	private String profileImage;
}