package com.example.BankingTransactionSystem.service;


import com.example.BankingTransactionSystem.dto.account.AccountRequest;
import com.example.BankingTransactionSystem.dto.account.AccountResponse;
import com.example.BankingTransactionSystem.entity.Account;
import com.example.BankingTransactionSystem.entity.UserEntity;
import com.example.BankingTransactionSystem.repository.AccountRepository;
import com.example.BankingTransactionSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    public AccountResponse createAcount(AccountRequest accountRequest){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));

        String accountNumber = "ACC :" + accountRequest.getAccountNumber();
        Account account = new Account();

       account.setAccountNumber(accountNumber);
        account.setAccountType(accountRequest.getAccountType());
        account.setCreatedAt(LocalDateTime.now());
        account.setBalance(accountRequest.getIntialBalance());

        account.setUser(user);

        Account savedAccount = accountRepository.save(account);


        return mapToResponse(savedAccount);
    }

    public List<AccountResponse> getAllAccounts(){
        return accountRepository.findAll().stream().map((acc)->mapToResponse(acc)).toList();
    }


    private AccountResponse mapToResponse(Account account) {

        AccountResponse accountResponse = new AccountResponse();

        accountResponse.setAccountNumber(account.getAccountNumber());
        accountResponse.setAccountType(account.getAccountType());
        accountResponse.setBalance(account.getBalance());
        accountResponse.setId(account.getId());

        return accountResponse;
    }




}
