package com.mathias.royalbankingapplication.infrastructure.controller;

import com.mathias.royalbankingapplication.payload.response.BankResponse;
import com.mathias.royalbankingapplication.service.GeneralUserService;
import com.mathias.royalbankingapplication.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class GeneralUserController {

    private final GeneralUserService generalUserService;

    @PutMapping("/{id}/profile-picture")
    public ResponseEntity<BankResponse<String>> updateProfilePicture(@PathVariable("id") Long id,
                                                                     @RequestBody MultipartFile profilePicture){
       if(profilePicture.getSize() > AppConstants.MAX_FILE_SIZE){
           return ResponseEntity
                   .status(HttpStatus.BAD_REQUEST)
                   .body(new BankResponse<>("File size exceed the normal limit"));
       }
       return generalUserService.uploadProfilePics(id, profilePicture);
    }
}
