package com.sparta.layered_unit_testing.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.layered_unit_testing.common.entity.ApiUseTime;
import com.sparta.layered_unit_testing.domain.user.entity.User;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
	Optional<ApiUseTime> findByUser(User user);
}
