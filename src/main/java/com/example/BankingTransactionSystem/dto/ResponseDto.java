package com.example.BankingTransactionSystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private String fullname;
    private String email;

    private String role;

}