package com.sparta.layered_unit_testing.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sparta.layered_unit_testing.common.entity.ApiUseTime;
import com.sparta.layered_unit_testing.common.repository.ApiUseTimeRepository;
import com.sparta.layered_unit_testing.domain.user.entity.User;
import com.sparta.layered_unit_testing.domain.user.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "AOP and Logback")
@Aspect
@Component
@RequiredArgsConstructor
public class ControllerTimeAop {

	private final ApiUseTimeRepository apiUseTimeRepository;

	@Pointcut("execution(* com.sparta.layered_unit_testing.domain.board.controller.*(..))")
	private void board() {}
	@Pointcut("execution(* com.sparta.layered_unit_testing.domain.comment.controller.*(..))")
	private void comment() {}
	@Pointcut("execution(* com.sparta.layered_unit_testing.domain.like.controller.*(..))")
	private void like() {}
	@Pointcut("execution(* com.sparta.layered_unit_testing.domain.mail.controller.*(..))")
	private void mail() {}
	@Pointcut("execution(* com.sparta.layered_unit_testing.domain.user.controller.*(..))")
	private void user() {}

	@Around("board() || comment() || like() || mail() || user()")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();

		try {
			Object output = joinPoint.proceed();
			return output;
		} finally {
			long endTime = System.currentTimeMillis();
			long runTime = endTime - startTime;

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class) {
				UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
				User loginUser = userDetails.getUser();

				ApiUseTime apiUseTime = apiUseTimeRepository.findByUser(loginUser).orElse(null);
				if (apiUseTime == null) {
					apiUseTime = new ApiUseTime(loginUser, runTime);
				} else {
					apiUseTime.addUseTime(runTime);
				}

				log.info("[API Use Time] UserId : " + loginUser.getUserId() + ", Total Time : " + apiUseTime.getTotaltime() + " ms");
				apiUseTimeRepository.save(apiUseTime);
			}
		}
	}
}