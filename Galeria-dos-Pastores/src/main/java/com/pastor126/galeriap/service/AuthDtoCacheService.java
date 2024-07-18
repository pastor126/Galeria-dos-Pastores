package com.pastor126.galeriap.service;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
public class AuthDtoCacheService {

    private CacheManager cacheManager;
    private Cache<String, String> authDtoCache;

    @PostConstruct
    public void init() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("authDtoCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(1000))
                                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(30, TimeUnit.MINUTES))))
                .build(true);
        authDtoCache = cacheManager.getCache("authDtoCache", String.class, String.class);
    }

    public void save(String id, String authDto) {
        authDtoCache.put(id, authDto);
    }

    public String get(String id) {
        return authDtoCache.get(id);
    }
}
