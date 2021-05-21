package indi.yufr.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Deque;

/**
 * @date: 2021/5/20 15:05
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApp.class})
@Slf4j
public class SupportedClassTest {

    @Autowired
    private Deque<String> redisQueue;

    @Test
    public void testRedisQueue() {
        // rpush queue-key "name"
        // lrange queue-key 0 -1
        redisQueue.add("name");

//        String pop = redisQueue.pop();
//        log.info(pop);
    }
}
