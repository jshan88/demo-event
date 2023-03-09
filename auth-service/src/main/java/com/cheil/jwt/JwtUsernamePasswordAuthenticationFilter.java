package com.cheil.jwt;

import com.cheil.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * !!!Description here
 *
 * @author : jshan
 * @created : 2023/03/08
 */
@RequiredArgsConstructor
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(),
                LoginRequest.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            return authentication;

        } catch (IOException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult) throws IOException, ServletException {

        String jwt = Jwts.builder()
            .setSubject(authResult.getName())
            .claim("authorities", authResult.getAuthorities())
            .setIssuedAt(new Date())
            .setExpiration(java.sql.Date.valueOf(
                LocalDate.now().plusDays(jwtConfig.getTokenExpirationPeriodInDays())))
            .signWith(secretKey)
            .compact();

        response.addHeader(HttpHeaders.AUTHORIZATION, jwtConfig.getTokenPrefix() + jwt);
    }
}
