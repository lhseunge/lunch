package com.fun.lunch.domain.works.service.impl;

import com.fun.lunch.domain.store.dto.StoreResponse;
import com.fun.lunch.domain.store.service.StoreService;
import com.fun.lunch.domain.works.dto.BotMessage;
import com.fun.lunch.domain.works.dto.BotMessageButtonAction;
import com.fun.lunch.domain.works.dto.ExceptionAlertRequest;
import com.fun.lunch.domain.works.service.WorksService;
import org.springframework.stereotype.Service;

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

        BotMessage<?>[] messages = {
                getLunchSticker(),
                getLunchText()
        };

        for (BotMessage<?> message : messages) {
            worksApi.sendWorksBotMessageToChannel(message);
        }

    }

    @Override
    public void sendException(ExceptionAlertRequest exceptionAlertRequest) {

        BotMessage<?> message = getExceptionAlert(exceptionAlertRequest);

        worksApi.sendWorksBotMessageToUser(message);

    }

    private BotMessage<?> getLunchText() {

        String personalKey = "k2systems";

        List<StoreResponse> stores = storeService.getStores(personalKey);
        StoreResponse anyStore = storeService.findAnyStore(stores);

        String todayMenu = anyStore.name();
        String content = String.format("오늘 점심은\n[%s]\n어떠세요?", todayMenu);

        storeService.saveDrawHistory(personalKey, anyStore);

//        List<BotMessageButtonAction> actions = List.of(new BotMessageButtonAction("uri", "통계 보기", "https://192.168.0.15:8814/statitics?personalKey=k2systems"));
        List<BotMessageButtonAction> actions = List.of(new BotMessageButtonAction("uri", "웹으로 가기", "https://192.168.0.15:8814/"));

        return BotMessage.button(content, actions);

    }

    private BotMessage<?> getLunchSticker() {

        String[][] stickers = new String[][]{
                {"620", "4"},
                {"1996", "446"},
                {"16769", "1029"},
                {"16770", "1029"},
                {"51626517", "11538"}
        };

        int index = ThreadLocalRandom.current().nextInt(stickers.length);

        return BotMessage.sticker(stickers[index]);

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
                
                DateTime \s
                > %s \s
                """
                , exceptionAlertRequest.getProject()
                , exceptionAlertRequest.getMethod() + " " + exceptionAlertRequest.getUri()
                , exceptionAlertRequest.getStackTrace()
                , exceptionAlertRequest.getUserId()
                , exceptionAlertRequest.getMessage()
                , new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(new Date())
        );

        return BotMessage.text(content);

    }

}
