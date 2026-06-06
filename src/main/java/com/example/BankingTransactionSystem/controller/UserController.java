package com.example.BankingTransactionSystem.controller;


import com.example.BankingTransactionSystem.dto.RequestDto;
import com.example.BankingTransactionSystem.dto.ResponseDto;
import com.example.BankingTransactionSystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private  final  UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register( @Valid  @RequestBody RequestDto requestDto){

        ResponseDto responseDto = userService.register(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/read")
    public ResponseEntity<List<ResponseDto>> getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }
}
