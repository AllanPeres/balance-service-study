package com.mentorshipwise.balanceservicestudy.services;

import com.mentorshipwise.balanceservicestudy.dtos.request.user.UpdateUserRequest;
import com.mentorshipwise.balanceservicestudy.dtos.request.user.UserRequest;
import com.mentorshipwise.balanceservicestudy.exceptions.user.UserExceptions;
import com.mentorshipwise.balanceservicestudy.models.User;
import com.mentorshipwise.balanceservicestudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User create(UserRequest req) {
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();

        return repository.save(user);
    }

    public User findById(String id) {
        return repository.findById(id)
                .orElseThrow(UserExceptions.UserNotFoundException::new);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(UserExceptions.UserNotFoundException::new);
    }

    public User updateUserById(String id, UpdateUserRequest userDetails) {
        User user = repository.findById(id)
                .orElseThrow(UserExceptions.UserNotFoundException::new);

        if (userDetails.getName() != null) {
            user.setName(userDetails.getName());
        }
        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }
        return repository.save(user);
    }

    public void deleteById(String id) {
        repository.findByEmail(id)
                .orElseThrow(UserExceptions.UserNotFoundException::new);
    }
}

