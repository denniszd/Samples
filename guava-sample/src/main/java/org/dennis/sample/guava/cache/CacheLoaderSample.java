package org.dennis.sample.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-05-03 19:23
 */
public class CacheLoaderSample {
    private LoadingCache<String, String> cache;

    public CacheLoaderSample() {
        cache = CacheBuilder.newBuilder().build(
                new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return "Hello, " + key;
                    }
                }
        );
    }

    public void put(String key,String value) {
        cache.put(key,value);
    }

    public String get(String key) throws ExecutionException {
        return cache.get(key);
    }

    public void close() {
        cache.cleanUp();
    }

    public static void main(String[] args) throws ExecutionException {
        CacheLoaderSample cacheLoaderSample = new CacheLoaderSample();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000;i++) {
            cacheLoaderSample.put("key" + i, "value" + i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("######################### puts elapsed： " + (endTime - startTime) + "ms");
        startTime = endTime;
        for (int i = 0; i < 1000000; i++) {
            System.out.println("key" + i + ": " + cacheLoaderSample.get("key" + i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("######################### gets elapsed： " + (endTime - startTime) + "ms");
        cacheLoaderSample.close();
    }
}
