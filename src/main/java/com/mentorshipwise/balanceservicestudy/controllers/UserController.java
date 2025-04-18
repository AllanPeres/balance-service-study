package com.mentorshipwise.balanceservicestudy.controllers;

import com.mentorshipwise.balanceservicestudy.model.User;
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
    // TODO: edit user
    // TODO: check how to return value in a nicer way, maybe response with message
    // TODO: check for form validation
    private final UserService service;

    @GetMapping
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody User user) {
        return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(
            @PathVariable String id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
