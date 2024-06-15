package com.sparta.layered_unit_testing.domain.mail.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.layered_unit_testing.domain.mail.dto.MailRequestDto;
import com.sparta.layered_unit_testing.domain.mail.dto.VerifyRequestDto;
import com.sparta.layered_unit_testing.domain.mail.service.MailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/email")
public class MailController {
	private final MailService mailService;

	@PostMapping("/send")
	public ResponseEntity<String> sendMail(@RequestBody MailRequestDto requestDto) throws MessagingException {
		mailService.sendMail(requestDto);
		return ResponseEntity.ok(requestDto.getEmail() + "로 인증코드가 발송되었습니다.");
	}

	@PostMapping("/verify")
	public ResponseEntity<String> verifyMail(@RequestBody VerifyRequestDto requestDto) {
		mailService.verifyMail(requestDto);
		return ResponseEntity.ok("입력하신 메일 " + requestDto.getEmail() + "이 정상적으로 인증되었습니다.");
	}
}
