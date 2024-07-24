package com.mathias.royalbankingapplication.service.impl;

import com.mathias.royalbankingapplication.domain.entity.UserEntity;
import com.mathias.royalbankingapplication.payload.response.BankResponse;
import com.mathias.royalbankingapplication.repository.UserRepository;
import com.mathias.royalbankingapplication.service.GeneralUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeneralUserServiceImpl implements GeneralUserService {
    private final FileUploadImpl fileUpload;
    private final UserRepository userRepository;


    @Override
    public ResponseEntity<BankResponse<String>> uploadProfilePics(Long id,MultipartFile multipartFile) {
        Optional<UserEntity> user = userRepository.findById(id);
        String fileUrl = null;

        try {
            if(user.isPresent()) {
              fileUrl = fileUpload.uploadFile(multipartFile);
              UserEntity userEntity = user.get();
              userEntity.setProfilePicture(fileUrl);

              userRepository.save(userEntity);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new BankResponse<>("You have Successfully uploaded your picture",fileUrl ));
    }
}
