package com.aleksandrm.mynotions.utils;

import com.aleksandrm.mynotions.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claim("email", user.getEmail())
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        JwtParser parser = Jwts.parser()
                .verifyWith(key)
                .build();

        try {
            parser.parseSignedClaims(token);
            return true;

        } catch (SignatureException e) {
            return false;

        } catch (ExpiredJwtException e) {
            return false;

        } catch (MalformedJwtException e) {
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        // TODO: твоя логика
        return (long) 0;
    }

    public boolean isTokenExpired(String token) {
        // TODO: твоя логика
        return false;
    }
}
