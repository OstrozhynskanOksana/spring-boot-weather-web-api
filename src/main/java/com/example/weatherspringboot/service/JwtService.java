package com.example.weatherspringboot.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@Slf4j
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private Key getSecretKey() {
        //hmacShaKeyFor створює криптографічний ключ для hmac алгоритмів із байтів
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateJwtToken(String email, String role) {
        return  Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)

                //збираємо токен в строку
                .compact();
    }
    public String getEmailFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()

                //HEADER.PAYLOAD(email..).SIGNATURE
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isValid(String token){
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

}
