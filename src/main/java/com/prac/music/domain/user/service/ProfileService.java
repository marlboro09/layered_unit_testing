package com.prac.music.domain.user.service;

import com.prac.music.common.service.S3Service;
import com.prac.music.domain.user.dto.ProfileRequestDto;
import com.prac.music.domain.user.dto.ProfileResponseDto;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.repository.UserRepository;
import com.prac.music.exception.UserNotFoundException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    // 유저 프로필 조회
    public ProfileResponseDto getProfile(User user) {
        User getUser = findUserById(user.getUserId());
        return new ProfileResponseDto(getUser);
    }

    // 유저 프로필 수정
    @Transactional
    public String updateProfile(ProfileRequestDto requestDto, User user, MultipartFile file) throws IOException {
        User getUser = findUserById(user.getUserId());
        String imageUrl = "";

        // Dto 에 비밀번호가 들어왔는지 검사
        Boolean ckePassword = validatePassword(requestDto.getPassword(), requestDto.getNewPassword(), getUser.getPassword());
        if(file != null && !file.isEmpty()) {
            imageUrl = s3Service.s3Upload(file);
        } else{
            imageUrl = "null";
        }

        // 비밀번호 인코딩 후 업데이트
        if (ckePassword) {
            String encodedPassword = passwordEncoder.encode(requestDto.getNewPassword());
            getUser.update(requestDto, encodedPassword,imageUrl);
        } else {
            getUser.update(requestDto,imageUrl);
        }

        return "프로필이 수정되었습니다.";
    }


    // 해당 유저 조희
    public User findUserById(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(()->
            new UserNotFoundException("등록된 회원이 아닙니다.")
        );
    }

    // 비밀번호 유효성 검사
    private Boolean validatePassword(String password, String newPassword, String userPassword) {
        if (StringUtils.isBlank(password) && StringUtils.isBlank(newPassword)) {
            return false; // 둘 다 비어있으면 검사하지 않음
        }
        if (StringUtils.isBlank(password)) {
            throw new NullPointerException("현재 비밀번호를 입력해주세요");
        }
        if (StringUtils.isBlank(newPassword)) {
            throw new NullPointerException("새 비밀번호를 입력해주세요");
        }
        if (!passwordEncoder.matches(password, userPassword)) {
            throw new IllegalArgumentException("현재 비밀번호와 일치하지 않습니다.");
        }
        if (passwordEncoder.matches(newPassword, userPassword)) {
            throw new IllegalArgumentException("현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.");
        }
        return true;
    }
}