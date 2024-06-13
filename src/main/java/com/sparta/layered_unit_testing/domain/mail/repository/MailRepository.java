package com.sparta.layered_unit_testing.domain.mail.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.layered_unit_testing.domain.mail.entity.Mail;

public interface MailRepository extends JpaRepository<Mail, Long> {
	Optional<Mail> findByEmail(String email);
}
