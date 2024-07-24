package com.mathias.royalbankingapplication.service.impl;

import com.mathias.royalbankingapplication.domain.entity.UserEntity;
import com.mathias.royalbankingapplication.domain.enums.Role;
import com.mathias.royalbankingapplication.payload.request.EmailDetails;
import com.mathias.royalbankingapplication.payload.request.LoginRequest;
import com.mathias.royalbankingapplication.payload.request.UserRequest;
import com.mathias.royalbankingapplication.payload.response.AccountInfo;
import com.mathias.royalbankingapplication.payload.response.ApiResponse;
import com.mathias.royalbankingapplication.payload.response.BankResponse;
import com.mathias.royalbankingapplication.payload.response.JwtAuthResponse;
import com.mathias.royalbankingapplication.repository.UserRepository;
import com.mathias.royalbankingapplication.service.AuthService;
import com.mathias.royalbankingapplication.service.EmailService;
import com.mathias.royalbankingapplication.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public BankResponse registerUser(UserRequest userRequest) {
        if(userRepository.existsByEmail(userRequest.getEmail())) {
            BankResponse response = BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .build();

            return response;
        }
        UserEntity userEntity = UserEntity.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .BVN(userRequest.getBVN())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .pin(userRequest.getPin())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .phoneNumber(userRequest.getPhoneNumber())
                .accountNumber(AccountUtils.generateAccountNumber())
                .status("ACTIVE")
                .accountbalance(BigDecimal.ZERO)
                .bankName("Royal Bank Limited")
                .profilePicture("https://res.cloudinary.com/dpfqbb9pl/image/upload/v1701260428/maleprofile_ffeep9.png")
                .role(Role.USER)
                .build();

        UserEntity savedUser = userRepository.save(userEntity);

        //AddEmail Alert

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("CONGRATULATIONS!!!! Your Account has been  Successfully created. \n Your Account Details:" +
                        " \n" + "Account Name: " + savedUser.getFirstName() + " " + savedUser.getOtherName() + " " + savedUser.getLastName() +
                        "\n Account Number: " + savedUser.getAccountNumber())

                .build();

        emailService.sendEmailAlert(emailDetails);


        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountbalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .bankName(savedUser.getBankName())
                        .accountName(savedUser.getFirstName() + " " +
                                savedUser.getLastName() + " " +
                                savedUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public ResponseEntity<ApiResponse<JwtAuthResponse>> loginUser(LoginRequest loginRequest) {
        Optional<UserEntity> userEntityOptional =
                userRepository.findByEmail(loginRequest.getEmail());


        EmailDetails loginAlert = EmailDetails.builder()
                .subject("You are logged in")
                .recipient(loginRequest.getEmail())
                .messageBody("You logged into your account. If you did not initiate this request, contact support desk")
                .build();

        emailService.sendEmailAlert(loginAlert);

        UserEntity user = userEntityOptional.get();


        return ResponseEntity.status(HttpStatus.OK)
                .body( new ApiResponse<>("Login Successfully", JwtAuthResponse.builder()
                        .accessToken("generate Token here")
                        .tokenType("Bearer")
                        .id(user.getId())
                        .email(user.getEmail())
                        .gender(user.getGender())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .profilePicture(user.getProfilePicture())
                        .build())
                );
    }
}
