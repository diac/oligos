package com.diac.oligos.apigateway.filter;

import com.diac.oligos.apigateway.model.Authority;
import com.diac.oligos.apigateway.model.ConnectionValidationResponse;
import com.diac.oligos.apigateway.model.ExceptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Component
@AllArgsConstructor
@Slf4j
public class AuthenticationPrefilter extends AbstractGatewayFilterFactory<AuthenticationPrefilter.Config> {

    @Qualifier("excludedUrls")
    private final List<String> excludedUrls;

    private final WebClient.Builder webClientBuilder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String bearerToken = request.getHeaders().getFirst("Authorization");
            if (isSecured().test(request)) {
                return webClientBuilder.build()
                        .get()
                        .uri("lb://authentication-service/api/v1/validateToken")
                        .header("Authorization", bearerToken)
                        .retrieve()
                        .bodyToMono(ConnectionValidationResponse.class)
                        .map(response -> {
                            exchange.getRequest()
                                    .mutate()
                                    .header("username", response.getUsername());
                            exchange.getRequest()
                                    .mutate()
                                    .header(
                                            "authorities",
                                            response.getAuthorities().stream()
                                                    .map(Authority::getAuthority)
                                                    .reduce("", (a, b) -> a + "," + b)
                                    );
                            exchange.getRequest()
                                    .mutate()
                                    .header("auth-token", response.getToken());
                            return exchange;
                        })
                        .flatMap(chain::filter).onErrorResume(error -> {
                            log.error("An error has occurred");
                            HttpStatusCode errorCode = null;
                            String errorMessage;
                            if (error instanceof WebClientRequestException) {
                                WebClientResponseException webClientResponseException = (WebClientResponseException) error;
                                errorCode = webClientResponseException.getStatusCode();
                                errorMessage = webClientResponseException.getStatusText();
                            } else {
                                errorCode = HttpStatus.BAD_GATEWAY;
                                errorMessage = HttpStatus.BAD_GATEWAY.getReasonPhrase();
                            }
                            return onError(
                                    exchange,
                                    String.valueOf(errorCode.value()),
                                    errorMessage,
                                    "JWT authentication failed",
                                    HttpStatus.valueOf(errorCode.value())
                            );
                        });
            }
            return chain.filter(exchange);
        };
    }


    private Predicate<ServerHttpRequest> isSecured() {
        return request -> excludedUrls.stream()
                .noneMatch(
                        uri -> request.getURI()
                                .getPath()
                                .contains(uri)
                );
    }

    private Mono<Void> onError(
            ServerWebExchange exchange,
            String errCode,
            String err,
            String errDetails,
            HttpStatus httpStatus
    ) {
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        try {
            response.getHeaders()
                    .add("Content-Type", "application/json");
            ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                    .errorCode(errCode)
                    .errorMessage(err)
                    .errorDetails(errDetails)
                    .date(new Date())
                    .build();
            byte[] exceptionResponseBytes = objectMapper.writeValueAsBytes(exceptionResponse);
            return response.writeWith(
                    Mono.just(exceptionResponseBytes)
                            .map(bytes -> dataBufferFactory.wrap(bytes))
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response.setComplete();
    }

    @NoArgsConstructor
    public static class Config {

    }
}