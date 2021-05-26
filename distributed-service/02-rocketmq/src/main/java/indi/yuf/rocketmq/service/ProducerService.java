package indi.yuf.rocketmq.service;

import indi.yuf.rocketmq.model.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @date: 2021/5/26 15:56
 * @author: farui.yu
 */
@Service
@Slf4j
public class ProducerService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * rocketMQTemplate.destroy() once use ,then we can not use rocketMQTemplate to send message again
     */
    public void convertAndSend() {
        // send message synchronously
        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
    }

    public void send() {
        //send spring message
        Message<OrderPaidEvent> event = MessageBuilder.withPayload(OrderPaidEvent.builder()
                .orderId("T_001")
                .paidMoney(BigDecimal.valueOf(88.8))
                .build())
                .build();

        rocketMQTemplate.send("test-topic-2", event);
    }

    public void asyncSend() {
        //send message asynchronously
        rocketMQTemplate.asyncSend("test-topic-3", new OrderPaidEvent("T_001", BigDecimal.valueOf(88.8)),
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult result) {
                        log.info("async onSuccess SendResult [{}]", result);
                    }

                    @Override
                    public void onException(Throwable error) {
                        log.error("async onException Throwable", error);
                    }

                });
    }


}
