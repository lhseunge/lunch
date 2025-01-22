package com.fun.lunch.domain.works.service.impl;

import com.fun.lunch.domain.store.service.StoreService;
import com.fun.lunch.domain.works.dto.BotMessageButtonAction;
import com.fun.lunch.domain.works.dto.BotMessage;
import com.fun.lunch.domain.works.dto.WorksAccessToken;
import com.fun.lunch.domain.works.dto.WorksAccessTokenRequest;
import com.fun.lunch.domain.works.service.WorksService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
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
    public void sendTodayLunch() {

        String accessToken = "Bearer " + getAccessToken().getAccessToken();

        sendLunchSticker(accessToken);
        sendLunchText(accessToken);

    }

    private void sendLunchText(String accessToken) {

        String todayStore = storeService.getRandomStore(storeService.getStores("k2systems")).name();
        String content = String.format("오늘 점심은\n[%s]\n어떠세요?", todayStore);

        List<BotMessageButtonAction> actions = List.of(new BotMessageButtonAction("uri", "재투표 하기", "https://192.168.0.15:8814/push"));
//        List<BotMessageButtonAction> actions = List.of(new BotMessageButtonAction("uri", "웹으로 가기", "https://192.168.0.15:8814/"));

        BotMessage<?> text = BotMessage.button(content, actions);

        worksApi.sendWorksBotMessage(accessToken, text);

    }

    private void sendLunchSticker(String accessToken) {

        String[][] stickers = worksApi.getHungerStickers();

        int index = ThreadLocalRandom.current().nextInt(stickers.length);

        BotMessage<?> sticker = BotMessage.sticker(stickers[index]);

        worksApi.sendWorksBotMessage(accessToken, sticker);
    }

}
