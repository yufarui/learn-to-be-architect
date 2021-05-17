package indi.yufr.redis;

import indi.yufr.redis.service.PubSubService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @date: 2021/5/14 15:17
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApp.class})
public class PubSubTest {

    @Autowired
    private PubSubService pubSubService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 响应 自定义pub
     * @see indi.yufr.redis.config.RedisSimpleListener
     *
     * 响应 keyExpire
     * @see org.springframework.data.redis.listener.KeyExpirationEventMessageListener#onMessage(Message, byte[])
     */
    @Test
    public void publishEvent() {
        pubSubService.publish("bar");
        stringRedisTemplate.opsForValue().set("name-1", "yufr", 1, TimeUnit.SECONDS);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }
}
