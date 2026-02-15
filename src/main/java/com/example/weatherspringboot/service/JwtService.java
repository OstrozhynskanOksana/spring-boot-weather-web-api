package com.example.weatherspringboot.service;

import com.example.weatherspringboot.dto.JwtAuthenticationDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    @Value("${jwt.secret}")
    private String jwt;

    public JwtAuthenticationDto generateAuthToken(String email){
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setToken(generateJwtToken(email));
        jwtAuthenticationDto.setRefreshToken(generateRefreshToken(email));
        return jwtAuthenticationDto;
    }
    public JwtAuthenticationDto refreshToken(String email, String refreshToken){
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setToken(generateJwtToken(email));
        jwtAuthenticationDto.setRefreshToken(refreshToken);
        return jwtAuthenticationDto;
    }

    public String getEmailFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()

                //HEADER.PAYLOAD(email..).SIGNATURE
                .parseClaimsJws(token)
                .getBody();
        //return email
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException expiredJwtException){
           log.error("Expired token", expiredJwtException);
        }
        catch (MalformedJwtException malformedJwtException){
            log.error("Malformed token", malformedJwtException);
        }
        catch (SecurityException securityException){
            log.error("Invalid signature", securityException);
        }
        catch (Exception e){
            log.error("Token validation error", e);
        }
        return false;
    }

    private String generateJwtToken(String email) {
        Date expiration = Date.from(Instant.now().plus(15, ChronoUnit.MINUTES));
        return  Jwts.builder()
                .setSubject(email)
                .setExpiration(expiration)
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)

                //збираємо токен в строку
                .compact();
    }
    private String generateRefreshToken(String email) {
        Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        return  Jwts.builder()
                .setSubject(email)
                .setExpiration(expiration)
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSecretKey() {
        //декодуємо ключ назад в байти
        byte[] keyBytes = Decoders.BASE64.decode(jwt);
        //hmacShaKeyFor створює криптографічний ключ для hmac алгоритмів із байтів
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
