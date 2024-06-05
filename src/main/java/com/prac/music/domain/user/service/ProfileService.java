package com.prac.music.domain.user.service;

import com.prac.music.domain.user.dto.ProfileRequestDto;
import com.prac.music.domain.user.dto.ProfileResponseDto;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    // 유저 프로필 조회
    public ProfileResponseDto getProfile(String userId) {
        User user = findUserById(userId);
        return new ProfileResponseDto(user);
    }

    // 유저 프로필 수정
    @Transactional
    public String updateProfile(String userId, ProfileRequestDto requestDto) {
        User user = findUserById(userId);

        // Dto 에 비밀번호가 들어왔을 경우
        validatePassword(requestDto.getPassword(), requestDto.getNewPassword(), user.getPassword());

        user.update(requestDto);
        return "프로필이 수정되었습니다.";
    }


    // 해당 유저 조희
    private User findUserById(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(()->
                new EntityNotFoundException("등록된 회원이 아닙니다.")
        );
    }

    // 비밀번호 유효성 검사
    private void validatePassword(String password, String newPassword, String userPassword) {
        if (StringUtils.isBlank(password) && StringUtils.isBlank(newPassword)) {
            return; // 둘 다 비어있으면 검사하지 않음
        }
        if (!StringUtils.isBlank(password)) {
            throw new NullPointerException("현재 비밀번호를 입력해주세요");
        }
        if (!StringUtils.isBlank(newPassword)) {
            throw new NullPointerException("새 비밀번호를 입력해주세요");
        }
        if (!userPassword.equals(password)) {
            throw new IllegalArgumentException("현재 비밀번호와 일치하지 않습니다.");
        }
        if (userPassword.equals(password)) {
            throw new IllegalArgumentException("현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.");
        }
    }
}
