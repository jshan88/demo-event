package com.cheil.jwt;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {


    //TODO : secretkey, tokenPrefix는 JwtConfig 같은걸로 빼서 구현하자.
    @Value("${jwt.secretKey}")
    private String secretKey;


    private Key key;

    //TODO : Secret Key도 @Bean으로 빼는게 나을듯. 해당 클래스 @Configuration으로 잡고.
    @PostConstruct
    public void init() {
        this.key = hmacShaKeyFor(secretKey.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getRole(String token) {
        return getAllClaimsFromToken(token).get("role").toString();
    }

    public void validateJwt(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException | ExpiredJwtException jwtException) {
            throw jwtException;
        }
    }
}
