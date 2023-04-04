package com.altice.shared;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LoadingCacheStore<T> {

    private LoadingCache<String, T> cache;
    private String parentClassName;

    public LoadingCacheStore(int expiryDuration, TimeUnit timeUnit, CacheLoader<String, T> loader) {
        parentClassName = loader.getClass().getName();
        log.debug("The LoadingCache in {} has been initialized.", parentClassName);
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build(loader);
    }

    public T get(String key) throws ExecutionException {
        return cache.get(key);
    }

    public void invalidateAll() {
        cache.invalidateAll();
        log.debug("The LoadingCache in {} has been invalidated.", parentClassName);
    }

}
