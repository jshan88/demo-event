package com.cheil.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * !!!Description here
 *
 * @author : jshan
 * @created : 2023/03/08
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ApiGWExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                ApiGWResponseTemplate apiGWResponseTemplate = createResponseTemplate(response, ex);
                byte[] errorResponse = objectMapper.writeValueAsBytes(apiGWResponseTemplate);
                return bufferFactory.wrap(errorResponse);
            } catch (Exception e) {
                log.error("error", e);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }

    private ApiGWResponseTemplate createResponseTemplate(ServerHttpResponse serverHttpResponse,
        Throwable ex) {
        List<Class<? extends RuntimeException>> jwtExceptionList =
            List.of(SignatureException.class, MalformedJwtException.class,
                UnsupportedJwtException.class, IllegalArgumentException.class);

        Class<? extends Throwable> thrownException = ex.getClass();

        ApiGWResponseTemplate apiGWResponseTemplate;

        if (thrownException == ExpiredJwtException.class) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            apiGWResponseTemplate = ApiGWResponseTemplate.builder().returnCode("ERROR")
                .returnMessage("Access Token Expired").build();
        } else if (jwtExceptionList.contains(thrownException)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            apiGWResponseTemplate = ApiGWResponseTemplate.builder().returnCode("ERROR")
                .returnMessage("Invalid Token").build();
        } else {
            serverHttpResponse.setStatusCode(serverHttpResponse.getStatusCode());
            apiGWResponseTemplate = ApiGWResponseTemplate.builder().returnCode("ERROR")
                .returnMessage(ex.getMessage()).build();
        }

        return apiGWResponseTemplate;
    }
}
