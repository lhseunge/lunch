package com.fun.lunch.controller;

import com.fun.lunch.service.impl.JwtTokenProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/works")
public class WorksController {

    private final JwtTokenProvider jwtTokenProvider;

    public WorksController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/pem")
    public String getPemValue() throws IOException {

        ClassPathResource resource = new ClassPathResource("keys/works_private.key");

        byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());

        return new String(bdata, StandardCharsets.UTF_8)
                .replaceAll("-----BEGIN PRIVATE KEY-----","")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .trim();

    }

    @GetMapping("/redirect")
    public String redirect() {

        return jwtTokenProvider.createAccessToken();

    }
}
