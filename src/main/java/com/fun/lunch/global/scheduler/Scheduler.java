package com.fun.lunch.global.scheduler;

import com.fun.lunch.domain.works.service.WorksService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final WorksService worksService;

    @Scheduled(cron = "0 20 10 * * Mon-Fri")
    public void sendTodayLunch() {
        worksService.sendTodayLunch();
    }

}
