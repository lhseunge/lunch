package com.fun.lunch.service.impl;

import com.fun.lunch.dto.BotMessageWrapper;
import com.fun.lunch.dto.WorksAccessToken;
import com.fun.lunch.dto.WorksAccessTokenRequest;
import com.fun.lunch.service.StoreService;
import com.fun.lunch.service.WorksService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class WorksServiceImpl implements WorksService {

    @Value("${works.auth.grantType}")
    private String grantType;
    @Value("${works.auth.clientId}")
    private String clientId;
    @Value("${works.auth.clientSecret}")
    private String clientSecret;
    @Value("${works.auth.scope}")
    private String scope;

    private final WebClient webClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final WorksApi worksApi;
    private final StoreService storeService;

    public WorksServiceImpl(@Qualifier("worksAuthBaseUrl") WebClient webClient, JwtTokenProvider jwtTokenProvider, WorksApi worksApi, StoreService storeService) {
        this.webClient = webClient;
        this.jwtTokenProvider = jwtTokenProvider;
        this.worksApi = worksApi;
        this.storeService = storeService;
    }

    @Override
    public WorksAccessToken getAccessToken() {

        WorksAccessTokenRequest tokenRequest = WorksAccessTokenRequest.builder()
                .assertion(jwtTokenProvider.buildAssertionToken())
                .grantType(grantType)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope(scope)
                .build();


        return webClient.post()
                .uri("/token")
                .bodyValue(tokenRequest.toFormDataMap())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                        .map(errorBody -> new RuntimeException("4xx error: " + errorBody)))
                .bodyToMono(WorksAccessToken.class)
                .block();

    }

    @Override
    public void sendBotMessage() {

        String accessToken = "Bearer " + getAccessToken().getAccessToken();

        sendSticker(accessToken);
        sendText(accessToken);


    }

    private void sendText(String accessToken) {

        String todayStore = storeService.getRandomStore(storeService.getStores("k2systems")).name();
        String content = String.format("오늘 점심은\n[%s]\n어떠세요?", todayStore);

        BotMessageWrapper<?> text = BotMessageWrapper.text(content);

        worksApi.sendWorksBotMessage(accessToken, text);

    }

    private void sendSticker(String accessToken) {

        String[][] stickers = worksApi.getHungerStickers();

        int index = ThreadLocalRandom.current().nextInt(stickers.length);

        BotMessageWrapper<?> sticker = BotMessageWrapper.sticker(stickers[index]);

        worksApi.sendWorksBotMessage(accessToken, sticker);
    }

}
