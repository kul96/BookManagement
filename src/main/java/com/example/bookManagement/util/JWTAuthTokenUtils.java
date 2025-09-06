package com.example.bookManagement.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTAuthTokenUtils {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60;// 1hour
    private static final String SECRETE = "this is kuldeep secrete so this is secured from all attacker length should >256bits";
    SecretKey key = Keys.hmacShaKeyFor(SECRETE.getBytes());

    public String generateJWTToken(String userName) {
        // check JWT.io and check token
        return Jwts.builder()
                   .setSubject(userName) // this should unique
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                   .signWith(key, SignatureAlgorithm.HS256)
                   .compact();
    }

    public String extractJWTTokenUserName(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    public boolean validateToken(String userName, UserDetails userDetails, String token) {
        // validate user
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                              .setSigningKey(key)
                              .build()
                              .parseClaimsJws(token)
                              .getBody()
                              .getExpiration();
        return expiration.before(new Date()); // not expiry return true
    }
}
