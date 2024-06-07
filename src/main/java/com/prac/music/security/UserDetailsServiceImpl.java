package com.prac.music.security;

import com.prac.music.domain.user.entity.User;
import com.prac.music.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new UsernameNotFoundException("Not Found " + userId));

        if (!user.isExist()) {
            throw new IllegalArgumentException("User : " + userId + " is not Exist");
        }

        return new UserDetailsImpl(user);
    }
}