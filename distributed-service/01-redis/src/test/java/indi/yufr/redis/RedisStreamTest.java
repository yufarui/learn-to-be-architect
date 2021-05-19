package indi.yufr.redis;

import indi.yufr.redis.service.RedisStreamAppending;
import indi.yufr.redis.service.RedisStreamConsuming;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @date: 2021/5/17 15:36
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApp.class})
public class RedisStreamTest {

    @Autowired
    private RedisStreamAppending redisStreamAppending;

    @Autowired
    private RedisStreamConsuming redisStreamConsuming;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void xaddTest() {
        redisStreamAppending.xadd("name-2", "yufr-2");

    }

    /**
     * object-mapping-test
     */
    @Test
    public void xadd2Test() {
        redisStreamAppending.xadd2("farui", "yu");
    }

    @Test
    public void xread2Test() {
        redisStreamConsuming.xread2();
    }

    @Test
    public void xrangeTest() {
        redisStreamAppending.xrange();
    }

    @Test
    public void xreadTest() {
        redisStreamConsuming.xread();
    }

    /**
     * readGroup 消费时触发
     *
     * @see indi.yufr.redis.config.StreamListenerConfig
     */
    @Test
    public void xreadGroupTest() {
        redisStreamAppending.xadd("farui", "yu");

        System.out.println("====第一次消费====");
        redisStreamConsuming.xreadGroup();

        System.out.println("====第二次消费====");
        // 无法再次读取出数据
        redisStreamConsuming.xreadGroup();
    }

    @Test
    public void xreadGroup2Test() {
        System.out.println("====xadd2====");
        redisStreamAppending.xadd2("farui", "yu");
        System.out.println("====xreadGroup2====");
        redisStreamConsuming.xreadGroup2();
    }

    @Test
    public void deleteAll() {
        List<MapRecord<String, Object, Object>> range = stringRedisTemplate.opsForStream()
                .range("my-stream", Range.unbounded());

        for (MapRecord<String, Object, Object> mapRecord : range) {
            stringRedisTemplate.opsForStream().delete("my-stream", mapRecord.getId());
        }
    }
}
