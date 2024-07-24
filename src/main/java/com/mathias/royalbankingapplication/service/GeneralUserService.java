package com.mathias.royalbankingapplication.service;

import com.mathias.royalbankingapplication.payload.response.BankResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface GeneralUserService {
    ResponseEntity<BankResponse<String>> uploadProfilePics(Long id,MultipartFile multipartFile);
}
