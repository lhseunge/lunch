package com.fun.lunch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Value("${works.baseUrl}")
    private String baseUrl;

    @Bean
    public WebClient worksWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(header -> {
                    header.add("Content-Type", "application/json");
                })
                .build();
    }

}
