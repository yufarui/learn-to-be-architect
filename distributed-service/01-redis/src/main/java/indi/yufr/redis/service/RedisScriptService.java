package indi.yufr.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @date: 2021/5/20 11:05
 * @author: farui.yu
 */
@Service
public class RedisScriptService {

    @Autowired
    @Qualifier("checkAndSetScript")
    private RedisScript<Boolean> checkAndSetScript;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Boolean checkAndSet(String expectedValue, String newValue) {
        return stringRedisTemplate.execute(checkAndSetScript, Collections.singletonList("foo"), expectedValue, newValue);
    }
}
