package com.example.BankingTransactionSystem.service.CustomUserService;

import com.example.BankingTransactionSystem.entity.UserEntity;
import com.example.BankingTransactionSystem.repository.UserRepository;
import com.example.BankingTransactionSystem.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ext.QNameSerializer;


@Service
@AllArgsConstructor
@EnableWebSecurity
public class CustomUserDetailService implements UserDetailsService {

        @Autowired
        private  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity  user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user not found"));

        return org.springframework.security.core.userdetails.User.builder().
                username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
