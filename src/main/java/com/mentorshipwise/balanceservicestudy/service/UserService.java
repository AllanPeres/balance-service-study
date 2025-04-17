package com.mentorshipwise.balanceservicestudy.service;

import com.mentorshipwise.balanceservicestudy.model.UserModel;
import com.mentorshipwise.balanceservicestudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<UserModel> findAll() {
        return repository.findAll();
    }

    public UserModel create(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public Optional<UserModel> findById(String id) {
        return repository.findById(id);
    }

    public Optional<UserModel> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}

