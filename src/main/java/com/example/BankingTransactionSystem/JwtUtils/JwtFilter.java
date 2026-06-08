package com.example.BankingTransactionSystem.JwtUtils;


import com.example.BankingTransactionSystem.service.CustomUserService.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

        @Autowired
        private JwtService jwtService;
        @Autowired
        private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Check if the Authorization header is missing or does not start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Pass the request to the next filter
            return;
        }

        // 2. Extract the actual JWT token string (skip "Bearer " prefix)
        jwt = authHeader.substring(7);

        // 3. Extract the email from the token using your JwtService
        userEmail = jwtService.extractEmail(jwt);

        // 4. If the email exists and the user is not already authenticated in the current security context
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 5. Load the user details from your database
            UserDetails userDetails = customUserDetailService.loadUserByUsername(userEmail);

            // 6. Validate the token using your updated JwtService method
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Create an authentication token for Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Build and set the request details (IP address, session details) into the authentication token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. Establish the user's security identity within Spring Security's context holder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 8. Continue down the filter chain execution line
        filterChain.doFilter(request, response);

    }
}

