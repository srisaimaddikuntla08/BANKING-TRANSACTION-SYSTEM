package com.example.BankingTransactionSystem.service;


import com.example.BankingTransactionSystem.dto.RequestDto;
import com.example.BankingTransactionSystem.dto.ResponseDto;
import com.example.BankingTransactionSystem.entity.UserEntity;
import com.example.BankingTransactionSystem.entity.UserRole;
import com.example.BankingTransactionSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public UserEntity register(RequestDto requestDto){

        if(userRepository.existsByEmail(requestDto.getEmail())){
             throw  new RuntimeException("email already exist try another email");
        }
        UserRole role = requestDto.getRole() != null ? UserRole.valueOf(requestDto.getRole()) : UserRole.CUSTOMER;

        UserEntity newUser = new UserEntity();

        newUser.setFullName(requestDto.getFullName());
        newUser.setEmail(requestDto.getEmail());
        newUser.setPassword(requestDto.getPassword());
        newUser.setRole(role);


        return userRepository.save(newUser);
    }


    public List<ResponseDto> getAllUsers(){
        return userRepository.findAll().stream().map((user)->mapToResponse(user)).toList();
    }

    public ResponseDto mapToResponse(UserEntity saveResponse){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setFullname(saveResponse.getFullName());
        responseDto.setEmail(saveResponse.getEmail());
        responseDto.setRole(String.valueOf(saveResponse.getRole()));
        return responseDto;
    }






}
