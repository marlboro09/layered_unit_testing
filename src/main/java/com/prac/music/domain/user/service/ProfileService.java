package com.prac.music.domain.user.service;

import com.prac.music.domain.user.dto.ProfileResponseDto;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.repsitory.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    // 유저 프로필 조회
    public ProfileResponseDto getProfile(String userId) {
        User user = findUserById(userId);
        return new ProfileResponseDto(user);
    }


    // 해당 유저 조희
    private User findUserById(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(()->
                new EntityNotFoundException("등록된 회원이 아닙니다.")
        );
    }
}
