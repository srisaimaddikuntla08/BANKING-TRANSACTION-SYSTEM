package com.example.BankingTransactionSystem.service;


import com.example.BankingTransactionSystem.JwtUtils.JwtService;
import com.example.BankingTransactionSystem.dto.LoginRequest;
import com.example.BankingTransactionSystem.dto.LoginResopnse;
import com.example.BankingTransactionSystem.dto.RequestDto;
import com.example.BankingTransactionSystem.dto.ResponseDto;
import com.example.BankingTransactionSystem.entity.UserEntity;
import com.example.BankingTransactionSystem.entity.UserRole;
import com.example.BankingTransactionSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final  UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtService jwtService;

    public ResponseDto register(RequestDto requestDto){

        if(userRepository.existsByEmail(requestDto.getEmail())){
             throw  new RuntimeException("email already exist try another email");
        }
        UserRole role = requestDto.getRole() != null ? UserRole.valueOf(requestDto.getRole()) : UserRole.CUSTOMER;

        UserEntity newUser = new UserEntity();

        newUser.setFullName(requestDto.getFullName());
        newUser.setEmail(requestDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        newUser.setRole(role);

        UserEntity savedUser = userRepository.save(newUser);
        return mapToResponse(savedUser);
    }

    public LoginResopnse login(LoginRequest loginRequest){
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new RuntimeException("user not found"));
        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw  new RuntimeException("invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResopnse(token);

    }


    public List<ResponseDto> getAllUsers(){
        return userRepository.findAll().stream().map((user)->mapToResponse(user)).toList();
    }


    private ResponseDto mapToResponse(UserEntity saveResponse){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setFullName(saveResponse.getFullName());
        responseDto.setEmail(saveResponse.getEmail());
        responseDto.setRole(String.valueOf(saveResponse.getRole()));
        return responseDto;
    }






}
