package com.example.rentappjava.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;

@Service
public class JwtProvider {
    @Value("${security.sigkey}")
    private String sigKey;
    @Value("${security.token.expiration}")
    private Long tokenExpirationTime;

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(((User) authentication.getPrincipal()).getUsername())
                .claim("role", "ROLE_" + authentication.getAuthorities().toString().replace("[", "").replace("]", "").trim())
                .setIssuedAt(from(Instant.now()))
                .setExpiration(from(Instant.now().plusSeconds(tokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS512, sigKey.getBytes())
                .compact();
    }

    public String generateTokenWithUserName(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", "ROLE_" + role)
                .setIssuedAt(from(Instant.now()))
                .signWith(SignatureAlgorithm.HS512, sigKey.getBytes())
                .setExpiration(from(Instant.now().plusSeconds(tokenExpirationTime)))
                .compact();
    }

    public boolean validateToken(String jwt) {
        parser().setSigningKey(sigKey.getBytes()).parseClaimsJws(jwt);
        return true;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parser()
                .setSigningKey(sigKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Long getTokenExpirationTime() {
        return tokenExpirationTime;
    }


}
