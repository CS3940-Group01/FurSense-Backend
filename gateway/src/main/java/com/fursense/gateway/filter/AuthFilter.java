package com.fursense.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final RouteValidator validator;
    private final WebClient webClient;

    @Autowired
    public AuthFilter(RouteValidator validator, WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.validator = validator;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8090/auth").build(); // your Auth service
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                String authHeader = exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new RuntimeException("Missing or invalid Authorization header");
                }

                String token = authHeader.substring(7);

                // Call Auth service to validate token via query param
                System.out.println(token);
                return webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/validate")
                                .queryParam("token", token)
                                .build())
                        .retrieve()
                        .bodyToMono(String.class)
                        .doOnNext(response -> System.out.println("Auth Service Response: " + response))
                        .then(chain.filter(exchange));
            }
            return chain.filter(exchange);
        };
    }


    public static class Config {}
}