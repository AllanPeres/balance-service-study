package com.mentorshipwise.balanceservicestudy.controllers;

import com.mentorshipwise.balanceservicestudy.dtos.request.user.LoginRequest;
import com.mentorshipwise.balanceservicestudy.dtos.response.ApiResponse;
import com.mentorshipwise.balanceservicestudy.dtos.response.user.AuthResponse;
import com.mentorshipwise.balanceservicestudy.dtos.response.user.UserResponse;
import com.mentorshipwise.balanceservicestudy.services.user.AuthService;
import com.mentorshipwise.balanceservicestudy.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest req) {
        AuthResponse auth = service.login(req.email(), req.password());
        return ResponseEntity.ok(ResponseUtil.success("Login successful", auth));

    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<UserResponse>> getAuthenticatedUser(HttpServletRequest request) {
        UserResponse user = service.getCurrentUser(request);
        return ResponseEntity.ok(ResponseUtil.success("Authenticated user:", user));
    }
}
