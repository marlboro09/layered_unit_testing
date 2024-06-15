package com.sparta.layered_unit_testing.domain.mail.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.layered_unit_testing.common.exception.MailServiceException;
import com.sparta.layered_unit_testing.domain.mail.dto.MailRequestDto;
import com.sparta.layered_unit_testing.domain.mail.dto.VerifyRequestDto;
import com.sparta.layered_unit_testing.domain.mail.entity.Mail;
import com.sparta.layered_unit_testing.domain.mail.repository.MailRepository;
import com.sparta.layered_unit_testing.domain.user.entity.User;
import com.sparta.layered_unit_testing.domain.user.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

	private static final int EXPIRED_TIME = 180; // 180초

	private final JavaMailSender javaMailSender;
	private final MailRepository mailRepository;
	private final UserRepository userRepository;

	@Async
	public CompletableFuture<Void> sendMail(MailRequestDto requestDto) {
		try {
			Mail mail = checkAndSaveMail(requestDto.getEmail());
			MimeMessage emailForm = createMailForm(mail);
			javaMailSender.send(emailForm);
		} catch (MessagingException e) {
			log.error("Failed to send email to {}", requestDto.getEmail(), e);
			throw new MailServiceException("이메일 전송에 실패했습니다.");
		}
		return CompletableFuture.completedFuture(null);
	}

	public void verifyMail(VerifyRequestDto requestDto) {
		LocalDateTime now = LocalDateTime.now();
		User user = getUserByEmail(requestDto.getEmail());
		Mail mail = mailRepository.findByEmail(user.getEmail()).orElseThrow(
			() -> new MailServiceException("잘못된 이메일입니다.")
		);

		LocalDateTime timeLimit = mail.getCreatedAt().plusSeconds(EXPIRED_TIME);
		String targetCode = mail.getCode();
		String code = requestDto.getCode();

		if (now.isAfter(timeLimit)) {
			throw new MailServiceException("만료된 인증코드입니다.");
		}

		if (targetCode.equals(code)) {
			user.updateStatusVeryfied();
		} else {
			throw new MailServiceException("인증코드가 올바르지 않습니다.");
		}
	}

	private String createCode() {
		int numberzero = 48; // 0 아스키 코드
		int alphbetz = 122; // z 아스키 코드
		int codeLength = 8; // 인증코드의 길이
		Random rand = new Random(); // 임의 생성

		return rand.ints(numberzero, alphbetz + 1)
			.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)) // 숫자와 알파벳만 허용
			.limit(codeLength)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
	}

	private MimeMessage createMailForm(Mail mail) throws MessagingException {
		String code = createCode();
		mail.mailAddCode(code);

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

		String senderEmail = "noreply_88@gmail.com";
		messageHelper.setFrom(senderEmail);
		messageHelper.setTo(mail.getEmail());
		messageHelper.setSubject("[88하게] 이메일 인증 번호 발송");

		String body = "<html><body style='background-color: #000000 !important; margin: 0 auto; max-width: 600px; word-break: break-all; padding-top: 50px; color: #ffffff;'>";
		body += "<h1 style='padding-top: 50px; font-size: 30px;'>이메일 주소 인증</h1>";
		body += "<p style='padding-top: 20px; font-size: 18px; opacity: 0.6; line-height: 30px; font-weight: 400;'>서비스 사용을 위해 회원가입 시 고객님께서 입력하신 이메일 주소의 인증이 필요합니다.<br />";
		body += "하단의 인증 번호로 이메일 인증을 완료하시면, 정상적으로 서비스를 이용하실 수 있습니다.<br />";
		body += "<div class='code-box' style='margin-top: 50px; padding-top: 20px; color: #000000; padding-bottom: 20px; font-size: 25px; text-align: center; background-color: #f4f4f4; border-radius: 10px;'>" + code + "</div>";
		body += "</body></html>";
		messageHelper.setText(body, true);

		return message;
	}

	private User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(
			() -> new MailServiceException(email + "에 해당하는 사용자를 찾을 수 없습니다.")
		);
	}

	private Mail checkAndSaveMail(String email) {
		User user = getUserByEmail(email);
		Optional<Mail> checkMail = mailRepository.findByEmail(user.getEmail());
		checkMail.ifPresent(mailRepository::delete);
		Mail mail = Mail.builder()
			.user(user)
			.build();

		return mailRepository.save(mail);
	}
}
