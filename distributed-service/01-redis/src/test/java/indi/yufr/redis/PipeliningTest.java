package indi.yufr.redis;

import indi.yufr.redis.service.PipeliningService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @date: 2021/5/20 10:06
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApp.class})
public class PipeliningTest {

    @Autowired
    private PipeliningService pipeliningService;

    @Test
    public void pipelining() {
        pipeliningService.putAll();
        pipeliningService.popAll();
    }
}
