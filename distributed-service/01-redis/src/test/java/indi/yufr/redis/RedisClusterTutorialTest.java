package indi.yufr.redis;

import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @date: 2021/5/12 16:44
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApp.class})
public class RedisClusterTutorialTest {

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOperations;

    @Test
    public void addAmountField() {
        valueOperations.set("name", "yufr-bigName");
        int i = 0;
        String last = valueOperations.get("__last__");

        System.out.println(last);
        if (!StringUtils.isBlank(last)) {
            i = Integer.parseInt(last);
        }

        for (; i < 1000; i++) {
            System.out.println(i);
            valueOperations.set("bar" + i, i + "");
            valueOperations.set("__last__", i + "");
        }
    }

}
