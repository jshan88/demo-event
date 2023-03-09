package com.cheil.sercurity;

import com.cheil.jwt.JwtConfig;
import com.cheil.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.cheil.service.ApplicationUserService;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //1111
        AuthenticationManager authenticationManager =
            authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        JwtUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter
            = new JwtUsernamePasswordAuthenticationFilter(authenticationManager, jwtConfig,
            secretKey);
        jwtUsernamePasswordAuthenticationFilter.setFilterProcessesUrl("/auth/login");

        http
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(jwtUsernamePasswordAuthenticationFilter)
//            .addFilterAfter(new JwtValidator(secretKey, jwtConfig), JwtUsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .anyRequest()
            .authenticated();

        return http.build();

//        http
//            .csrf().disable()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
//            .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig),JwtUsernameAndPasswordAuthenticationFilter.class)
//            .authorizeRequests()
//            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
//            .antMatchers("/api/**").hasRole(STUDENT.name())
//            .anyRequest()
//            .authenticated();

    }

    //1111
    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public AuthenticationManager authenticationManager() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//        daoAuthenticationProvider.setUserDetailsService(applicationUserService);
//        return new ProviderManager(daoAuthenticationProvider);
//    }
}
