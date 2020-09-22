package com.vito.jnotsj.auth;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vito.jnotsj.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
@ConfigurationProperties("nots.security")
public class JwtTokenProvider {
    @Value("${security.jwtSecret}")
    private String jwtSecret;

    ObjectMapper objectMapper = new ObjectMapper();

    @Value("${security.jwtAccessExpirationMins}")
    private int jwtAccessTokenExpirationMins;

    @Value("${security.jwtRefreshExpirationMins}")
    private int jwtRefreshTokenExpirationMins;

    @SneakyThrows
    public String generateAccessToken(Authentication authentication) {
        UserAuth userDetails = (UserAuth) authentication.getPrincipal();
        User user = userDetails.getUser();
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime expirationDate = dateNow.plusMinutes(jwtAccessTokenExpirationMins);

        return Jwts.builder()
                .setSubject(objectMapper.writeValueAsString(user))
                .setIssuedAt(Date.from(dateNow.toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(expirationDate.toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }


    public UserAuth getUserFromJWT(String token) throws IOException {
        String userAuthString = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        if(userAuthString == null) {
            throw new IllegalArgumentException();
        }
        User user = objectMapper.readValue(userAuthString, User.class);

        return UserAuth.create(user);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {}
        return false;
    }
}