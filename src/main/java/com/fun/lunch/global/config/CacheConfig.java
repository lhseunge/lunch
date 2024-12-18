package com.fun.lunch.global.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<CaffeineCache> caches = Arrays.stream(CacheType.values())
                .map(cache -> new CaffeineCache(cache.getName(), Caffeine.newBuilder().recordStats()
                                .expireAfterWrite(cache.getExpireAfterWrite(), TimeUnit.SECONDS)
                                .maximumSize(cache.getMaximumSize())
                                .build()
                        )
                )
                .toList();

        cacheManager.setCaches(caches);

        return cacheManager;
    }
}
