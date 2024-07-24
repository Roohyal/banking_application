package com.mathias.royalbankingapplication.repository;

import com.mathias.royalbankingapplication.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //checks if the user has an email
    Boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByAccountNumber(String accountNumber);

    UserEntity findByAccountNumber(String accountNumber);
}
