package com.fun.lunch.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${works.auth.clientId}")
    private String clientId;

    @Value("${works.auth.serviceAccount}")
    private String serviceAccount;

    @Value("${jwt.expiration_time}")
    private long accessTokenExpTime;

    private final PrivateKey privateKey;

    public JwtTokenProvider(
            @Value("${jwt.privateKeyPath}") String privateKeyPath
    ) throws Exception {

        ClassPathResource resource = new ClassPathResource(privateKeyPath);

        byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());

        String privateKey = new String(bdata, StandardCharsets.UTF_8)
                .replaceAll("-----BEGIN PRIVATE KEY-----","")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .trim();



        byte[] keyBytes = Decoders.BASE64.decode(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
    }

    /**
     * Access Token 생성
     * @return Access Token String
     */
    public String createAccessToken() {
        return createToken(accessTokenExpTime);
    }


    /**
     * JWT 생성
     * @param expireTime
     * @return JWT String
     */
    private String createToken(long expireTime) {

        Instant iat = Instant.now();
        String jwt =  Jwts.builder()
                .header()
                        .add("typ","JWT")
                        .and()
                .issuer(clientId)
                .subject(serviceAccount)
                .issuedAt(Date.from(iat))
                .expiration(Date.from(iat.plusSeconds(expireTime)))
                .signWith(privateKey)
                .compact();

        System.out.println("jwt = " + jwt);

        return jwt;
    }

}

