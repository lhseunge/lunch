package com.fun.lunch.domain.works.service.impl;

import com.fun.lunch.domain.store.service.StoreService;
import com.fun.lunch.domain.works.dto.*;
import com.fun.lunch.domain.works.service.WorksService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WorksServiceImpl implements WorksService {

    private final WorksApi worksApi;
    private final StoreService storeService;

    public WorksServiceImpl(WorksApi worksApi, StoreService storeService) {
        this.worksApi = worksApi;
        this.storeService = storeService;
    }

    @Override
    public void sendTodayLunch() {

        String accessToken = "Bearer " + getAccessToken().getAccessToken();

        sendLunchSticker(accessToken);
        sendLunchText(accessToken);

    }

    @Override
    public void sendException(ExceptionAlertRequest exceptionAlertRequest) {

        String accessToken = "Bearer " + getAccessToken().getAccessToken();

        sendExceptionAlert(accessToken, exceptionAlertRequest);

    }

    private BotMessage<?> getLunchText() {

        String todayStore = storeService.getRandomStore(storeService.getStores("k2systems")).name();
        String content = String.format("오늘 점심은\n[%s]\n어떠세요?", todayStore);

//        List<BotMessageButtonAction> actions = List.of(new BotMessageButtonAction("uri", "재투표 하기", "https://192.168.0.15:8814/works/push"));
        List<BotMessageButtonAction> actions = List.of(new BotMessageButtonAction("uri", "웹으로 가기", "https://192.168.0.15:8814/"));

        BotMessage<?> text = BotMessage.button(content, actions);

        worksApi.sendWorksBotMessageToChannel(accessToken, text);

    }

    private BotMessage<?> getLunchSticker() {

        String[][] stickers = worksApi.getHungerStickers();

        int index = ThreadLocalRandom.current().nextInt(stickers.length);

        BotMessage<?> sticker = BotMessage.sticker(stickers[index]);

        worksApi.sendWorksBotMessageToChannel(accessToken, sticker);
    }

    private BotMessage<?> getExceptionAlert(ExceptionAlertRequest exceptionAlertRequest) {

        String content = String.format(
                """
                [%s] Exception Handled. \s
                
                ---------------------------
                
                API \s
                > %s \s
                
                StackTrace \s
                > %s \s
                
                UserId \s
                > %s \s
                
                Massage \s
                > %s \s
                
                Date \s
                > %s \s
                """
                , exceptionAlertRequest.getProject()
                , exceptionAlertRequest.getMethod() + " " + exceptionAlertRequest.getUri()
                , exceptionAlertRequest.getStackTrace()
                , exceptionAlertRequest.getUserId()
                , exceptionAlertRequest.getMessage()
                , new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(new Date())
        );

        BotMessage<?> text = BotMessage.text(content);

        worksApi.sendWorksBotMessageToUser(accessToken, text);
    }

}
