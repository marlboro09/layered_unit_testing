package com.prac.music.domain.user.service;

import com.prac.music.domain.user.dto.SignupRequestDto;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.repsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(SignupRequestDto requestDto) {
        String userId = requestDto.getUserId();
        String password = passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(password);

        Optional<User> checkUser = userRepository.findByUserId(userId);

        if(checkUser.isPresent()){
            throw new IllegalArgumentException("이미 중복된 사용자가 존재합니다.");
        }

        User user = new User(requestDto);
        return userRepository.save(user);
    }
}
