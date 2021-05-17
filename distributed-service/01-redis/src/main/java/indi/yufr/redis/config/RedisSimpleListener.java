package indi.yufr.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @date: 2021/5/14 15:41
 * @author: farui.yu
 */
@Component
public class RedisSimpleListener {

    private static Logger logger = LoggerFactory.getLogger(RedisSimpleListener.class);

    // redis 订阅信息 次数限制
    private CountDownLatch latch = new CountDownLatch(10);

    public void handleMsg(String message) {
        logger.info("receive msg :" + message);
    }
}
