package com.example.BankingTransactionSystem.dto.account;


import lombok.Data;

@Data
public class AccountRequest {

    private String accountNumber;
    private Double intialBalance;

    private String accountType;
}
