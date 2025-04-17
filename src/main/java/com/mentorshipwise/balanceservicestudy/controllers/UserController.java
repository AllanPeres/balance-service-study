package com.mentorshipwise.balanceservicestudy.controllers;

import com.mentorshipwise.balanceservicestudy.model.UserModel;
import com.mentorshipwise.balanceservicestudy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserModel> getAllUsers() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(
            @RequestBody UserModel user) {
        return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUser(
            @PathVariable String id) {
        return service.findById(id).map(ResponseEntity:: ok).orElse(ResponseEntity.notFound().build());
    }

}
