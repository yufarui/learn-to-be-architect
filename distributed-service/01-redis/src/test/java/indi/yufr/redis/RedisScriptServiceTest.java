package indi.yufr.redis;

import indi.yufr.redis.service.RedisScriptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @date: 2021/5/20 11:09
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApp.class})
public class RedisScriptServiceTest {

    @Autowired
    private RedisScriptService redisScriptService;

    @Test
    public void checkAndSet() {
        Boolean result = redisScriptService.checkAndSet("bar", "bar-1");
        System.out.println(result);
    }
}
