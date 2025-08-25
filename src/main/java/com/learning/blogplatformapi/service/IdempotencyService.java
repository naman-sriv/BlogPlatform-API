package com.learning.blogplatformapi.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class IdempotencyService {

    private final Cache<String, String> idempotencyCache;

    public IdempotencyService(Cache<String, String> idempotencyCache) {
        this.idempotencyCache=idempotencyCache;
    }

    public boolean setIfNotExists(String key){
        return idempotencyCache.asMap().putIfAbsent(key,"progressing")==null;
    }

    public String getResponse(String key) {
        return idempotencyCache.getIfPresent(key);
    }

    public void saveResponse(String key, String response) {
        idempotencyCache.put(key,response);
    }
}
