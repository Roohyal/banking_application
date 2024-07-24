package com.mathias.royalbankingapplication.domain.entity;

import com.mathias.royalbankingapplication.domain.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "user_tbl")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity extends BaseClass{
    private String firstName;


    private String lastName;

    private String otherName;

    private String email;

    private String password;

    private String address;

    private String phoneNumber;

    private String stateOfOrigin;

    private String gender;

    private String BVN;

    private String pin;

    private String accountNumber;

    private String bankName;

    private String profilePicture;

    private String status;

    private Role role;

    private BigDecimal accountbalance;

}
