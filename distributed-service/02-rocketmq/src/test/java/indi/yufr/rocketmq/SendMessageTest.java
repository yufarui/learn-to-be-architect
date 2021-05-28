package indi.yufr.rocketmq;

import indi.yuf.rocketmq.RocketMQApp;
import indi.yuf.rocketmq.service.ProducerService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @date: 2021/5/26 8:44
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RocketMQApp.class})
public class SendMessageTest {

    @Autowired
    private ProducerService producerService;

    @Test
    public void sendMessage() throws Exception {
//        producerService.convertAndSend();
//        producerService.send();
//        producerService.asyncSend();
//        producerService.transSend();
        producerService.requestString();
//        producerService.requestObject();
        Thread.sleep(5000);
    }
}
