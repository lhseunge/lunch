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
                    header.add("Authorization", "Bearer kr1AAABLUn2hywkjZz7ToH8Cx8z5pgt3lxwypF8tULvbzm3ROfnL6hHkvdRz2FZF2mveNnoCXwx3twZ1xdRxX1kWgWDVCdSGWwkwy1xsCR6Bwvo0hXnyv9AcbUhjPHWwFw/RgwdJstrdCAmLP5/C+0wl4yB0Jx91NKpxdz3KN8MbyrOkr/q3kOteEQM8tA9VWiEub/6ooyFQ7ANr4n9xayiuEHHBdPgyIr4582lByIhinQdZVszzIPD/aZ54RvyoB7frvOX5KHHlT1Duq1FIgKpuCBBtsN/c0tUnoh+U8e0nAIFmatp4994oizJz5AmF3Tn28PBTT03YMYnNsyfiSvkxFfxfBFPpwmBl9VCMLSCMjbbHpMeZ0Z22PsH/CczDUik/wTUimV6fhg3zWaq0nCSabLOeQo=.kwiu9yNovfcs8Rumz2QSOg");
                })
                .build();
    }

}
