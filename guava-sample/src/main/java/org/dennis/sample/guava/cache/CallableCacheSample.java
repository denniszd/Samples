package org.dennis.sample.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-05-03 19:39
 */
public class CallableCacheSample {
    private Cache<String, String> cache;

    public CallableCacheSample() {
        cache = CacheBuilder.newBuilder().maximumSize(2000).expireAfterWrite(1000, TimeUnit.SECONDS).build();
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }

    public String get(final String key) throws ExecutionException {
        return get(key, new Callable<String>() {
            public String call() throws Exception {
                return "hello " + key;
            }
        });
    }

    public String get(String key, Callable<String> callable) throws ExecutionException {
        return cache.get(key, callable);
    }

    public void close() {
        cache.cleanUp();
    }

    public static void main(String[] args) throws ExecutionException {
        CallableCacheSample callableCacheSample = new CallableCacheSample();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            callableCacheSample.put("key" + i, "value" + i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("######################### puts elapsed： " + (endTime - startTime) + "ms");
        startTime = endTime;
        for (int i = 0; i < 1000; i++) {
            System.out.println("key" + i + ": " + callableCacheSample.get("key" + i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("######################### gets elapsed： " + (endTime - startTime) + "ms");
        callableCacheSample.close();
    }
}
