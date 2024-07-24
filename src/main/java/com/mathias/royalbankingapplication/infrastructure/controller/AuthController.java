package com.mathias.royalbankingapplication.infrastructure.controller;

import com.mathias.royalbankingapplication.payload.request.LoginRequest;
import com.mathias.royalbankingapplication.payload.request.UserRequest;
import com.mathias.royalbankingapplication.payload.response.ApiResponse;
import com.mathias.royalbankingapplication.payload.response.BankResponse;
import com.mathias.royalbankingapplication.payload.response.JwtAuthResponse;
import com.mathias.royalbankingapplication.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register-user")
    public BankResponse createUserAccount(@Valid@RequestBody UserRequest userRequest) {

        return authService.registerUser(userRequest);
    }

    @PostMapping("/login-user")
    public ResponseEntity<ApiResponse<JwtAuthResponse>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }
}
