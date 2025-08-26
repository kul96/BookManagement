package com.example.bookManagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheInspectionService {

    @Autowired
    private CacheManager cacheManager;

    public String printCacheContent(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            log.info("cache content");
            log.info(cache.getNativeCache()
                          .toString());
            return cache.getNativeCache()
                        .toString();
        } else {
            log.info("cache is empty " + cacheName);
            return "cache is empty " + cacheName;
        }
    }
}
