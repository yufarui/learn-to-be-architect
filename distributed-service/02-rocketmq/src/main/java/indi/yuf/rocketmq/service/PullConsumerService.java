package indi.yuf.rocketmq.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @date: 2021/5/26 16:54
 * @author: farui.yu
 */
//@Service
@Slf4j
public class PullConsumerService implements CommandLineRunner {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    // topic test-topic-3
    @Override
    public void run(String... args) throws Exception {
        //This is an example of pull consumer using rocketMQTemplate.
        List<String> messages = rocketMQTemplate.receive(String.class);
        log.info("pull-consumer received message {}", messages);
    }
}
