package com.example.BankingTransactionSystem.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class RequestDto {

    private String fullName;

    private String email;
    private String password;

    private String role;

}
