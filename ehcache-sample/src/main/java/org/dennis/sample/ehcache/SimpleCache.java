package org.dennis.sample.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-05-03 15:05
 */
public class SimpleCache {
    private CacheManager cacheManager;
    private Cache<String, String> cache;

    public SimpleCache() {
        this(20);
    }

    public SimpleCache(int cacheSize) {
        String cacheName = "preConfigured";
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache(cacheName,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(cacheSize)))
                .build();
        cacheManager.init();
        cache = cacheManager.getCache(cacheName, String.class, String.class);
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }

    public String get(String key) {
        return cache.get(key);
    }

    public void close() {
        cache.clear();
        cacheManager.close();
    }

    public static void main(String[] args) {
        SimpleCache cache = new SimpleCache(2000);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            cache.put("key" + i,"value" + i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("######################### puts elapsed： " + (endTime - startTime) + "ms");
        startTime = endTime;
        for (int i = 0; i < 1000000; i++) {
            System.out.println("key" + i + ": " + cache.get("key" + i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("######################### gets elapsed： " + (endTime - startTime) + "ms");
        cache.close();
    }
}
