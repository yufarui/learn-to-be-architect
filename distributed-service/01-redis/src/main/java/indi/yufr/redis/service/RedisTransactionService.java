package indi.yufr.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @date: 2021/5/19 18:48
 * @author: farui.yu
 */
@Service
public class RedisTransactionService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 在多节点集群模式下,无法使用transaction
     */
    @Deprecated
    @Transactional
    public void transaction() {
        stringRedisTemplate.opsForValue().set("error-1", "error-1");

        stringRedisTemplate.opsForValue().set("big-error-1", "big-error-1");

        throw new RuntimeException("测试用的错误");
    }

}
