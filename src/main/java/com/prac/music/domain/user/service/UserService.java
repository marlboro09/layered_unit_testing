package com.prac.music.domain.user.service;

import com.prac.music.domain.user.dto.LoginRequestDto;
import com.prac.music.domain.user.dto.LoginResponseDto;
import com.prac.music.domain.user.dto.SignupRequestDto;
import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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
        User user = this.userRepository.findByUserId(requestDto.getUserId()).orElseThrow(
                () -> new UsernameNotFoundException("아이디를 다시 확인해주세요")
        );

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUserId(),
                        requestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.createToken(requestDto.getUserId());
        String refreshToken = jwtService.createRefreshToken(requestDto.getUserId());
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        String retoken = token.substring(7);
        jwtService.isTokenExpired(retoken);
        jwtService.isRefreshTokenExpired(refreshToken);

        return new LoginResponseDto(token, "로그인에 성공했습니다.");
    }

}
