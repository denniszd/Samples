package org.dennis.sample.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-05-03 16:20
 */
public class XmlConfiguredCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlConfiguredCache.class);
    private CacheManager cacheManager;
    private Cache<String, String> cache;

    public XmlConfiguredCache() {
        this("/ehcache.xml");
    }

    public XmlConfiguredCache(String configFilePath) {
        URL url = this.getClass().getResource(configFilePath);
        Configuration configuration = new XmlConfiguration(url);
        cacheManager = CacheManagerBuilder.newCacheManager(configuration);
        cacheManager.init();
        cache = cacheManager.getCache("foo", String.class, String.class);
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

    public static void main(String[] args) throws InterruptedException {
        XmlConfiguredCache cache = new XmlConfiguredCache();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            cache.put("key" + i, "value" + i);
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("######################### puts elapsed： " + (endTime - startTime) + "ms");
        startTime = endTime;
        for (int i = 0; i < 1000; i++) {
            LOGGER.info("key" + i + ": " + cache.get("key" + i));
        }
        endTime = System.currentTimeMillis();
        LOGGER.info("######################### gets elapsed： " + (endTime - startTime) + "ms");
        Thread.sleep(9 * 1000);
        LOGGER.info("key1: " + cache.get("key1"));
        cache.close();
    }
}
