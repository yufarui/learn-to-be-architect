package jndi.yu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class JedisSingleTest {
    public static final JedisPoolConfig JEDIS_POOL_CONFIG = new JedisPoolConfig();

    static {
        JEDIS_POOL_CONFIG.setMaxTotal(20);
        JEDIS_POOL_CONFIG.setMaxIdle(10);
        JEDIS_POOL_CONFIG.setMinIdle(5);
    }

    public static void main(String[] args) {

        Jedis jedis = null;
        // 使用docker快速创建服务
        try (JedisPool jedisPool = new JedisPool(JEDIS_POOL_CONFIG, "127.0.0.1", 6379, 3000, null)) {
            jedis = jedisPool.getResource();
            luaCommandTest(jedis);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    private static void stringCommandTest(Jedis jedis) {
        System.out.println(jedis.set("name", "simple_jedis"));
        System.out.println(jedis.get("name"));
    }

    private static void pipelinedCommandTest(Jedis jedis) {

        Pipeline pipelined = jedis.pipelined();
        for (int i = 0; i < 10; i++) {

            pipelined.incr("pipeline_key");
            pipelined.setnx("key_" + i, String.valueOf(new Random(100).nextInt()));
        }

        List<Object> objects = pipelined.syncAndReturnAll();
        System.out.println(objects);
    }

    private static void luaCommandTest(Jedis jedis) {

        jedis.set("simple_count", 10 + "");

        String script = "local key = KEYS[1]\n" +
                "local val = redis.call(\"GET\", key);\n" +
                "local a = tonumber(val)\n" +
                "local b = tonumber(ARGV[1])\n" +
                "\n" +
                "if a >= b\n" +
                "then\n" +
                "        redis.call('SET', KEYS[1], a + b)\n" +
                "        return 1\n" +
                "else\n" +
                "        return 0\n" +
                "end\n";

        jedis.eval(script,
                Collections.singletonList("simple_count"),
                Collections.singletonList("2"));

        System.out.println(jedis.get("simple_count"));
    }
}