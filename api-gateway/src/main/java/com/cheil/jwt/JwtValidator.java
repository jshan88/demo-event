package com.cheil.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtValidator {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    //TODO : secretkey, tokenPrefix는 JwtConfig 같은걸로 빼서 구현하자.
//    @Value("${jwt.secretKey}")
//    private String secretKey;

//    //TODO : Secret Key도 @Bean으로 빼는게 나을듯. 해당 클래스 @Configuration으로 잡고.
//    private Key key;
//    @PostConstruct
//    public void init() {
//        this.key = hmacShaKeyFor(secretKey.getBytes());
//    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
            .getBody();
    }

    public String getAuthorities(String token) {
        return getAllClaimsFromToken(token).get("authorities").toString();
    }

    public void validateJwt(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException | ExpiredJwtException jwtException) {
            throw jwtException;
        }
    }
}
