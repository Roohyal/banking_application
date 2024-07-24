package com.mathias.royalbankingapplication.service;

import com.mathias.royalbankingapplication.payload.request.LoginRequest;
import com.mathias.royalbankingapplication.payload.request.UserRequest;
import com.mathias.royalbankingapplication.payload.response.ApiResponse;
import com.mathias.royalbankingapplication.payload.response.BankResponse;
import com.mathias.royalbankingapplication.payload.response.JwtAuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
BankResponse registerUser(UserRequest userRequest);
ResponseEntity<ApiResponse<JwtAuthResponse>> loginUser(LoginRequest loginRequest);
}
