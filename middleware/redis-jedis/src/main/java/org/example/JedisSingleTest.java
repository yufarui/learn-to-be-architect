package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisSingleTest {
    public static final JedisPoolConfig JEDIS_POOL_CONFIG = new JedisPoolConfig();

    static {
        JEDIS_POOL_CONFIG.setMaxTotal(20);
        JEDIS_POOL_CONFIG.setMaxIdle(10);
        JEDIS_POOL_CONFIG.setMinIdle(5);
    }

    public static void main(String[] args) {

        // 使用docker快速创建服务
        try (JedisPool jedisPool = new JedisPool(JEDIS_POOL_CONFIG, "127.0.0.1", 6379, 3000, null)) {
            stringCommandTest(jedisPool);
        }
    }

    private static void stringCommandTest(JedisPool jedisPool) {
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.set("name", "simple_jedis"));
        System.out.println(jedis.get("name"));
    }

}