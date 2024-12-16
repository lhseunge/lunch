package com.fun.lunch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@AllArgsConstructor
@Builder
public class WorksAccessTokenRequest {

    private String assertion;
    private String grantType;
    private String clientId;
    private String clientSecret;
    private String scope;


    public MultiValueMap<String, String> toFormDataMap() {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("assertion", assertion);
        map.add("grant_type", grantType);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("scope", scope);

        return map;

    }

}
