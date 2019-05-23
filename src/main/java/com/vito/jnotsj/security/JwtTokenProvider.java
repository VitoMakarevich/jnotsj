package com.vito.jnotsj.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

@Component
@ConfigurationProperties("nots.security")
public class JwtTokenProvider {
    @Value("${security.jwtSecret}")
    private String jwtSecret;

    @Value("${security.jwtExpirationMins}")
    private int jwtExpirationMins;

    public String generateToken(Authentication authentication) {
        UserAuth user = (UserAuth) authentication.getPrincipal();
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime expirationDate = dateNow.plusMinutes(jwtExpirationMins);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(Date.from(dateNow.toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(expirationDate.toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {}
        return false;
    }
}