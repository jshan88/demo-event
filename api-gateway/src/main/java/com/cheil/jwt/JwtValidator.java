package com.cheil.jwt;

import static io.jsonwebtoken.security.Keys.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    @Value("${jwt.secretKey}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = hmacShaKeyFor(secret.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public String getUserName(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getRole(String token) {
        return getAllClaimsFromToken(token).get("role").toString();
    }

    public boolean validateJwt(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException
                 | IllegalArgumentException | ExpiredJwtException jwtException) {
            return false;
        }
        return true;
    }
}
