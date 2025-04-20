package com.mentorshipwise.balanceservicestudy.services;

import com.mentorshipwise.balanceservicestudy.dtos.UpdateUserRequest;
import com.mentorshipwise.balanceservicestudy.dtos.UserRequest;
import com.mentorshipwise.balanceservicestudy.exceptions.UserNotFoundException;
import com.mentorshipwise.balanceservicestudy.models.User;
import com.mentorshipwise.balanceservicestudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    // TODO: check way to avoid code repetition

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

    public User findById(String id) throws UserNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public User findByEmail(String email) throws UserNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public User updateUserById(String id, UpdateUserRequest userDetails) throws UserNotFoundException {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (userDetails.getName() != null) {
            user.setName(userDetails.getName());
        }
        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }
        return repository.save(user);
    }

    public void deleteById(String id) throws UserNotFoundException {
        repository.findByEmail(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }
}

