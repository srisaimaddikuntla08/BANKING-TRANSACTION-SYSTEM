package com.example.BankingTransactionSystem.controller;


import com.example.BankingTransactionSystem.dto.RequestDto;
import com.example.BankingTransactionSystem.dto.ResponseDto;
import com.example.BankingTransactionSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private  UserService userService;

    public ResponseEntity<ResponseDto> register(@RequestBody RequestDto requestDto){

        ResponseDto responseDto = new ResponseDto();

        responseDto.setEmail(requestDto.getEmail());
        responseDto.setFullname(requestDto.getFullName());
        responseDto.setRole(requestDto.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    public ResponseEntity<List<ResponseDto>> getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }
}
