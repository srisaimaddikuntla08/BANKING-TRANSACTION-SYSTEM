package com.example.BankingTransactionSystem.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String accountNumber;

    private String accountType;

    private Double balance;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
