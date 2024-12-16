package com.fun.lunch.scheduler;

import com.fun.lunch.service.WorksService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private final WorksService worksService;

    public Scheduler(
            WorksService worksService
    ) {
        this.worksService = worksService;
    }

    @Scheduled(cron = "0/5 * * * * *")
//    @Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 20 10 * * *")
    public void sendTodayLunch() {
        worksService.sendBotMessage();
    }

}
