package com.mentorshipwise.balanceservicestudy.service;

import com.mentorshipwise.balanceservicestudy.model.UserModel;
import com.mentorshipwise.balanceservicestudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository repository;


    public List<UserModel> findAll() {
        return repository.findAll();
    }

    public UserModel create(UserModel user) {
        return repository.save(user);
    }

    public Optional<UserModel> findById(String id) {
        return repository.findById(id);
    }

    public Optional<UserModel> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}

