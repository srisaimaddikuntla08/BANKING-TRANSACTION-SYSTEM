package com.example.BankingTransactionSystem.controller;


import com.example.BankingTransactionSystem.dto.account.AccountRequest;
import com.example.BankingTransactionSystem.dto.account.AccountResponse;
import com.example.BankingTransactionSystem.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest){

        return ResponseEntity.ok(accountService.createAcount(accountRequest));
    }

    @GetMapping("/get")
    public ResponseEntity<List<AccountResponse>> getAllUsers(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}
