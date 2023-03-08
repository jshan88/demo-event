package com.cheil.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * !!!Description here
 *
 * @author : jshan
 * @created : 2023/03/08
 */
@Getter
@NoArgsConstructor
@Configuration
public class JwtConfig {

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;
    @Value("${jwt.tokenExpirationPeriodInDays")
    private int tokenExpirationPeriodInDays;
}
