package indi.yufr.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @date: 2021/5/20 9:47
 * @author: farui.yu
 */
@Service
@Slf4j
public class PipeliningService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void putAll() {
        stringRedisTemplate.opsForList().rightPushAll("my-queue", "foo1", "foo2", "foo3");
    }

    /**
     * @see RedisConnection#openPipeline()
     * 执行pipeline,但是你无法获取执行结果
     */
    public void popAll() {
        List<Object> results = stringRedisTemplate.executePipelined(
                (RedisCallback<Object>) connection -> {
                    StringRedisConnection stringRedisConn = (StringRedisConnection) connection;

                    for (int i = 0; i < 3; i++) {
                        String value = stringRedisConn.rPop("my-queue");
                        // 你无法在 pipeline 中 获取结果
                        log.info(value);
                    }
                    // 这里必须为 null;
                    return null;
                });

        // 这里可以按顺序获取结果
        // foo3 foo2 foo1
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }
    }
}
