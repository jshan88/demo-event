package com.cheil.filter;

import com.cheil.jwt.JwtValidator;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthenticationFilter extends
    AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtValidator jwtValidator;

    static class Config {
    }

    public AuthenticationFilter(JwtValidator jwtValidator) {
        super(Config.class);
        this.jwtValidator = jwtValidator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("AuthenticationFilter starts : request -> {}", request);

            if (!containsAuthorization(request)) {
                return onError(exchange, "Authorization header is missing"
                );
            }

            String token = getToken(request);

            // validateJwt() 에서 Exception 발생 시, error exception handler 통해 응답하는 것으로 수정
            // Unathorized는 제외. (onError에서 처리함.)
            jwtValidator.validateJwt(token);

            // APP-MEMBERSHIP 이랑 타 어플리케이션 구분하기 위해 일단 넣어둠.
            if (!jwtValidator.getRole(token).contains("admin")) {
                return onError(exchange, "Admin 권한 없음");
            }

            this.mutateRequestWithHeaders(exchange, token);

            log.info("Authentication Filter Done.");
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error) {
        log.error(error);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean containsAuthorization(ServerHttpRequest request) {
        return request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }

    private String getToken(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0)
            .replace("Bearer", "");
    }

    /// API Gateway에서 권한 분기 부족한 부분이 있을 수 있음.
    /// 뒷단 어플리케이션 컨트롤러에서 @RequestHeader로 토큰 내 role 받아서 추가 제어 여지 주려함
    private void mutateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtValidator.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
            .header("username", claims.getSubject())
            .header("role", String.valueOf(claims.get("role")))
            .build();
    }

}
