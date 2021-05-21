package indi.yufr.redis;

import indi.yufr.redis.service.RedisCrudService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @date: 2021/5/21 9:30
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApp.class})
public class RedisCrudServiceTest {

    @Autowired
    private RedisCrudService redisCrudService;

    @Test
    public void testRedisCrud() {
        redisCrudService.repoTest();
    }
}
