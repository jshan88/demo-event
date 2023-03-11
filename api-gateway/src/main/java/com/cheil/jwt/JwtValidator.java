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

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
            .getBody();
    }

    public String getAuthorities(String token) {
        return getAllClaimsFromToken(token).get("authorities").toString();
    }

    //TODO: 이거 필요 여부 다시 확인. ApiGWExceptionHandler 가 얘 없이도 익셉션 캐치 하는지 확인.
    public void validateJwt(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException | ExpiredJwtException jwtException) {
            throw jwtException;
        }
    }
}
