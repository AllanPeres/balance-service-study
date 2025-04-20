package com.mentorshipwise.balanceservicestudy.controllers;

import com.mentorshipwise.balanceservicestudy.dtos.ApiResponse;
import com.mentorshipwise.balanceservicestudy.dtos.UpdateUserRequest;
import com.mentorshipwise.balanceservicestudy.dtos.UserRequest;
import com.mentorshipwise.balanceservicestudy.exceptions.UserNotFoundException;
import com.mentorshipwise.balanceservicestudy.models.User;
import com.mentorshipwise.balanceservicestudy.services.UserService;
import com.mentorshipwise.balanceservicestudy.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = service.findAll();
        String message = users.isEmpty()
                ? "No users found"
                : "Users found successfully";
        return ResponseEntity.ok(
                ResponseUtil.success(message, users)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(
            @Valid @RequestBody UserRequest user) {
        User createdUser = service.create(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseUtil.success("User created successfully", createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<User>>> getUser(
            @PathVariable String id) throws UserNotFoundException {
        Optional<User> user = Optional.ofNullable(service.findById(id));
        return ResponseEntity.ok(ResponseUtil.success("User:", user));
    }

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<Optional<User>>> getUserByEmail(
            @PathVariable String email) throws UserNotFoundException {
        Optional<User> user = Optional.ofNullable(service.findByEmail(email));
        return ResponseEntity.ok(ResponseUtil.success("User found:", user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<User>>> updateUserById(
            @PathVariable String id,
            @Valid @RequestBody UpdateUserRequest userDetails
    ) throws UserNotFoundException {
        Optional<User> user = Optional.ofNullable(service.updateUserById(id, userDetails));
        return ResponseEntity.ok(ResponseUtil.success("User updated", user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable String id) throws UserNotFoundException {
        service.deleteById(id);
        return ResponseEntity.ok(ResponseUtil.success("User deleted", null));
    }

}
