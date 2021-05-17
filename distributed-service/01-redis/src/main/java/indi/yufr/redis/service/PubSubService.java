package indi.yufr.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * @date: 2021/5/14 13:33
 * @author: farui.yu
 */
@Component
public class PubSubService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void publish(String message) {
        stringRedisTemplate.convertAndSend("foo", message);
    }

}
