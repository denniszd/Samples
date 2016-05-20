package org.dennis.sample.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-05-06 16:43
 */
public class JedisSentinelTest {
    public static void main(String[] args) {
        Set<String> sentinels = new HashSet<String>(2);
        sentinels.add(new HostAndPort("192.168.48.223", 26379).toString());
        sentinels.add(new HostAndPort("192.168.48.223", 26380).toString());
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("master1", sentinels);
        System.out.println("Current master: " + jedisSentinelPool.getCurrentHostMaster().toString());
        Jedis master = jedisSentinelPool.getResource();
        master.set("username","dennis");
        master.close();
        Jedis master2 = jedisSentinelPool.getResource();
        String value = master2.get("username");
        System.out.println("username: " + value);
        master2.close();
        jedisSentinelPool.destroy();
    }
}
