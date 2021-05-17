package indi.yufr.redis;

import indi.yufr.redis.service.RedisStreamAppending;
import indi.yufr.redis.service.RedisStreamConsuming;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void xaddTest() {
        redisStreamAppending.xadd("name-2", "yufr-2");
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
     * @see indi.yufr.redis.config.StreamListenerConfig
     */
    @Test
    public void xreadGroupTest() {
        redisStreamAppending.xadd("name-3", "yufr-3");

        System.out.println("====第一次消费====");
        redisStreamConsuming.xreadGroup();

        System.out.println("====第二次消费====");
        // 无法再次读取出数据
        redisStreamConsuming.xreadGroup();
    }
}
