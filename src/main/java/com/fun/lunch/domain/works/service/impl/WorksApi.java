package com.fun.lunch.domain.works.service.impl;

import com.fun.lunch.domain.works.dto.BotMessageWrapper;
import com.fun.lunch.global.exception.CustomExceptionEnum;
import com.fun.lunch.global.exception.ResponseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WorksApi {

    @Value("${works.api.botId}")
    private String botId;

    private final WebClient webClient;

    public WorksApi(@Qualifier("worksApiBaseUrl") WebClient webClient) {
        this.webClient = webClient;
    }

    public void sendWorksBotMessage(String accessToken, BotMessageWrapper<?> content) {

        String channelId = "15e30482-5dd0-a8a9-086e-f65f3974f603"; // prod
//        String channelId = "0781e7d9-6c8b-270b-bffc-83be2de971ff"; // dev

        webClient.post()
                .uri("/bots/{botId}/channels/{channelId}/messages", botId, channelId)
                .headers(header -> header.add("Authorization", accessToken))
                .bodyValue(content)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                        .map(errorBody -> ResponseException.from(CustomExceptionEnum.WORKS_EXCEPTION, "4xx error: " + errorBody)))
                .bodyToMono(String.class)
                .block();
    }

    public String[][] getHungerStickers() {
        return new String[][]{
                {"620", "4"},
                {"1996", "446"},
                {"16769", "1029"},
                {"16770", "1029"},
                {"51626517", "11538"}
        };
    }

}