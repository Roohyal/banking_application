package com.mathias.royalbankingapplication.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotBlank( message = "FirstName should not be blank")
    @Size(min = 2, max = 125, message = "FirstName must be at least 2 characters long")
    private String firstName;
    @NotBlank( message = "LastName should not be blank")
    @Size(min = 2, max = 125, message = "LastName must be at least 2 characters long")
    private String lastName;

    private String otherName;

    private String email;

    private String address;

    private String stateOfOrigin;

    private String BVN;

    private String phoneNumber;

    private String pin;

    private String gender;

    private String password;
}
