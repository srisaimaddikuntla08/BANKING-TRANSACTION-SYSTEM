package com.example.BankingTransactionSystem.repository;

import com.example.BankingTransactionSystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

}
