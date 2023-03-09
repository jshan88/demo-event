package com.cheil.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * !!!Description here
 *
 * @author : jshan
 * @created : 2023/03/09
 */
@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private int tokenExpirationPeriodInDays;
}
