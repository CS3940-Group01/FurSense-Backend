package com.fursense.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;


@Component
public class JwtUtil {
    // Using a securely generated key for HS256
    public static final Key SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Validate the token
    public void validateToken(String token) {
        System.out.println("Validating token: " + token);
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(SECRET)  // Use the key directly
                .build()
                .parseClaimsJws(token);
    }
}