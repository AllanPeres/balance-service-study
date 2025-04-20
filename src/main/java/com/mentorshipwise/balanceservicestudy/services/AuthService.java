package com.mentorshipwise.balanceservicestudy.services;

import com.mentorshipwise.balanceservicestudy.dtos.response.AuthResponse;
import com.mentorshipwise.balanceservicestudy.dtos.response.UserResponse;
import com.mentorshipwise.balanceservicestudy.exceptions.UserExceptions;
import com.mentorshipwise.balanceservicestudy.models.User;
import com.mentorshipwise.balanceservicestudy.repository.UserRepository;
import com.mentorshipwise.balanceservicestudy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(UserExceptions.UserNotFoundException::new);

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new UserExceptions.InvalidCredentials();
        }
        String token = jwtUtil.generateJwtToken((user));
        return new AuthResponse(token);
    }

    public UserResponse getCurrentUser(HttpServletRequest req) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new UserExceptions.InvalidCredentials();
        }
        return UserResponse.from(user);
    }
}
