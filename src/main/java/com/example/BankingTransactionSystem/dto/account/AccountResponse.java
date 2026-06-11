package com.example.BankingTransactionSystem.dto.account;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long id;

    private String accountNumber;

    private String accountType;

    private Double balance;

}
