package com.fun.lunch.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WorksConfig {

    @Value("${works.auth.baseUrl}")
    private String authBaseUrl;

    @Value("${works.api.baseUrl}")
    private String apiBaseUrl;

    @Bean
    @Qualifier("worksAuthBaseUrl")
    public WebClient authWebClient() {
        return WebClient.builder()
                .baseUrl(authBaseUrl)
                .defaultHeaders(header -> {
                    header.add("Content-Type", "application/x-www-form-urlencoded");
                })
                .build();
    }

    @Bean
    @Qualifier("worksApiBaseUrl")
    public WebClient botWebClient() {
        return WebClient.builder()
                .baseUrl(apiBaseUrl)
                .defaultHeaders(header -> {
                    header.add("Content-Type", "application/json");
                })
                .build();
    }

}
