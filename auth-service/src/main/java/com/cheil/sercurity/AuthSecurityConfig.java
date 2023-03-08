package com.cheil.sercurity;

import com.cheil.service.AuthService;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * !!!Description here
 *
 * @author : jshan
 * @created : 2023/03/08
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final SecretKey secretKey;

}
