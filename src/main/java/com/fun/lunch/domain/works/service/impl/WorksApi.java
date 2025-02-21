package com.fun.lunch.domain.works.service.impl;

import com.fun.lunch.domain.works.dto.BotMessage;
import com.fun.lunch.domain.works.dto.WorksAccessToken;
import com.fun.lunch.domain.works.dto.WorksAccessTokenRequest;
import com.fun.lunch.global.exception.CustomExceptionEnum;
import com.fun.lunch.global.exception.ResponseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WorksApi {

    // Auth
    @Value("${works.auth.grantType}")
    private String grantType;
    @Value("${works.auth.clientId}")
    private String clientId;
    @Value("${works.auth.clientSecret}")
    private String clientSecret;
    @Value("${works.auth.scope}")
    private String scope;

    // Bot
    @Value("${works.api.botId}")
    private String botId;

    private final WebClient botWebClient;
    private final WebClient authWebClient;
    private final JwtTokenProvider jwtTokenProvider;

    public WorksApi(
            @Qualifier("worksBotClient") WebClient botWebClient,
            @Qualifier("worksAuthClient") WebClient authWebClient,
            JwtTokenProvider jwtTokenProvider) {
        this.botWebClient = botWebClient;
        this.authWebClient = authWebClient;
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    @Async("threadPoolTaskExecutor")
    public void sendWorksBotMessageToChannel(BotMessage<?> content) {

        String accessToken = generateToken().getAccessToken();
        String channelId = "15e30482-5dd0-a8a9-086e-f65f3974f603"; // 직장인 고민거리 방

        botWebClient.post()
                .uri("/bots/{botId}/channels/{channelId}/messages", botId, channelId)
                .headers(header -> header.add("Authorization", accessToken))
                .bodyValue(content)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                        .map(errorBody -> ResponseException.from(CustomExceptionEnum.WORKS_EXCEPTION, "4xx error: " + errorBody)))
                .bodyToMono(String.class)
                .block();
    }

    @Async("threadPoolTaskExecutor")
    public void sendWorksBotMessageToUser(BotMessage<?> content) {

        String accessToken = generateToken().getAccessToken();
        String userId = "0781e7d9-6c8b-270b-bffc-83be2de971ff"; // 밥 방

        botWebClient.post()
                .uri("/bots/{botId}/channels/{userId}/messages", botId, userId)
                .headers(header -> header.add("Authorization", accessToken))
                .bodyValue(content)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                        .map(errorBody -> ResponseException.from(CustomExceptionEnum.WORKS_EXCEPTION, "4xx error: " + errorBody)))
                .bodyToMono(String.class)
                .block();
    }

    public WorksAccessToken generateToken() {
        WorksAccessTokenRequest tokenRequest = WorksAccessTokenRequest.builder()
                .assertion(jwtTokenProvider.buildAssertionToken())
                .grantType(grantType)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope(scope)
                .build();

        return authWebClient.post()
                .uri("/token")
                .bodyValue(tokenRequest.toFormDataMap())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                        .map(errorBody -> new RuntimeException("4xx error: " + errorBody)))
                .bodyToMono(WorksAccessToken.class)
                .block();
    }
}