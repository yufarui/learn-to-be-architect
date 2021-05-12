package indi.yufr.redis;

import indi.yufr.redis.service.ExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @date: 2021/5/12 16:44
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApp.class})
public class RedisTest {

    @Resource(name="stringRedisTemplate")
    private ValueOperations<String, String> stringValueOperations;

    @Resource(name="redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @Test
    public void test() {
        stringValueOperations.set("name", "yufr-bigName");
        valueOperations.set("name", "yufr");
        System.out.println(stringValueOperations.get("name"));
        System.out.println(valueOperations.get("name"));
    }

}
