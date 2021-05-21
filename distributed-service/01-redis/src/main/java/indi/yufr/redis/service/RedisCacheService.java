package indi.yufr.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @date: 2021/5/20 16:57
 * @author: farui.yu
 */
@Service
@Slf4j
public class RedisCacheService {

    @Cacheable(value = "cache", key = "#id")
    public String cacheAble(String id) {
        return "able-auto-generate";
    }

    @CachePut(value = "cache", key = "#id")
    public String cachePut(String id) {
        return "put-auto-generate";
    }

    @CacheEvict(value = "cache", key = "#id")
    public void cacheEvict(String id) {
        log.info("cache-evict[{}]", id);
    }
}
