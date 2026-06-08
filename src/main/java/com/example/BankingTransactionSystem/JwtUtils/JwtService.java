package com.example.BankingTransactionSystem.JwtUtils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    String secret = "YYYUEIIIEIEIIEIEIEIEIIEIEIEEIIEIEIIIEIE" ;


    private Key getKey(){
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public  String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }


    public String extractEmail(String token){
        return  extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        Date expirationToken = extractClaims(token).getExpiration();
        return  expirationToken.before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final  String email = extractEmail(token);

        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
