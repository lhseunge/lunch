package com.fun.lunch.scheduler;

import com.fun.lunch.dto.BotMessageWrapper;
import com.fun.lunch.service.StoreService;
import com.fun.lunch.service.impl.WorksApi;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class Scheduler {

    private final StoreService storeService;
    private final WorksApi worksApi;

    public Scheduler(
            StoreService storeService,
            WorksApi worksApi
    ) {
        this.storeService = storeService;
        this.worksApi = worksApi;
    }

    @Scheduled(cron = "0/5 * * * * *")
//    @Scheduled(cron = "0 * * * * *")
//    @Scheduled(cron = "0 20 11 * * *")
    public void sendTodayLunch() {
        sendSticker();
        sendText();
    }

    private void sendSticker() {

        String[][] stickers = worksApi.getHungerStickers();

        int index = ThreadLocalRandom.current().nextInt(stickers.length);

        BotMessageWrapper<?> sticker = BotMessageWrapper.sticker(stickers[index]);

        worksApi.sendWorksBotMessage(sticker);

    }


    private void sendText() {

        String todayStore = storeService.getRandomStore(storeService.getStores("k2systems")).name();
        String content = String.format("오늘 점심은\n[%s]\n어떠세요?", todayStore);

        BotMessageWrapper<?> text = BotMessageWrapper.text(content);

        worksApi.sendWorksBotMessage(text);

    }
}
