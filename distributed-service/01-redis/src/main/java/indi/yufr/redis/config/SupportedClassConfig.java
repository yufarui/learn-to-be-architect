package indi.yufr.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.RedisList;

/**
 * @date: 2021/5/20 14:58
 * @author: farui.yu
 */
@Configuration
public class SupportedClassConfig {

    @Bean("redisQueue")
    public RedisList<String> queue(StringRedisTemplate stringRedisTemplate) {
        RedisList<String> redisList = new DefaultRedisList<>("queue-key", stringRedisTemplate);
        return redisList;
    }

}
