package com.cheil.jwt;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * !!!Description here
 *
 * @author : jshan
 * @created : 2023/03/09
 */
@RequiredArgsConstructor
@Configuration
public class JwtSecretKey {

    private final JwtConfig jwtConfig;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
    }
}
