package indi.yufr.redis;

import indi.yufr.redis.service.RedisCacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @date: 2021/5/20 17:19
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApp.class})
public class RedisCacheTest {

    @Autowired
    private RedisCacheService redisCacheService;

    @Test
    public void cacheTest() {

        // del cache::put-test
        redisCacheService.cacheEvict("put-test");

        // set cache::put-test put-auto-generate
        System.out.println(redisCacheService.cachePut("put-test"));

        // get cache::put-test
        System.out.println(redisCacheService.cacheAble("put-test"));

        redisCacheService.cacheEvict("put-test");

        // set cache::put-test able-auto-generate
        System.out.println(redisCacheService.cacheAble("put-test"));
    }
}
