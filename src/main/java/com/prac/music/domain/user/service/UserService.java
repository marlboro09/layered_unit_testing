package com.prac.music.domain.user.service;

import com.prac.music.domain.user.dto.LoginRequestDto;
import com.prac.music.domain.user.dto.LoginResponseDto;
import com.prac.music.domain.user.dto.SignupRequestDto;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.repsitory.UserRepository;
import com.prac.music.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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

    public LoginResponseDto loginUser(@RequestBody LoginRequestDto requestDto){
        User user = userRepository.findByUserId(requestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("아이디를 다시 확인해 주세요")
        );

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호를 다시 확인해주세요");
        }

        String token = jwtUtil.createToken(user.getUserId(), user.getUserStatusEnum());

        return new LoginResponseDto(user, token);
    }
}
