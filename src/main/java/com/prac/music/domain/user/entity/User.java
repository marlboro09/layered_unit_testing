package com.prac.music.domain.user.entity;

import com.prac.music.domain.user.dto.ProfileRequestDto;
import com.prac.music.domain.user.dto.SignupRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false, name = "userid")
	private String userId;

	@Column(unique = true, nullable = false, name = "email")
	private String email;

	@Column(nullable = false, name = "password")
	private String password;

	@Column(nullable = false, name = "name")
	private String name;

	@Column(nullable = false, name = "intro")
	private String intro;

	@Column(nullable = false, name = "status")
	private UserStatusEnum userStatusEnum;

	@Column(name = "refresh_token")
	private String refreshToken;

	public boolean isAdmin() {
		return this.userStatusEnum == UserStatusEnum.SECESSION;
	}

	public User(SignupRequestDto requestDto) {
		this.userId = requestDto.getUserId();
		this.name = requestDto.getName();
		this.email = requestDto.getEmail();
		this.password = requestDto.getPassword();
		this.intro = requestDto.getIntro();
		this.userStatusEnum = UserStatusEnum.NORMAL;
	}

	public void update(ProfileRequestDto requestDto) {
		this.name = requestDto.getName();
		this.email = requestDto.getEmail();
		this.intro = requestDto.getIntro();
	}

	public void update(ProfileRequestDto requestDto, String encodedPasswdDto) {
		this.name = requestDto.getName();
		this.email = requestDto.getEmail();
		this.intro = requestDto.getIntro();
		this.password = encodedPasswdDto;
	}
}
