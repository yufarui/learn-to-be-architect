package indi.yuf.rocketmq.service;

import indi.yuf.rocketmq.model.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @date: 2021/5/26 16:25
 * @author: farui.yu
 */
public class PushConsumerService {

    @Slf4j
    @Service
    @RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic-1")
    public static class MyConsumer1 implements RocketMQListener<String> {
        public void onMessage(String message) {
            log.info("received message: {}", message);
        }
    }

    @Slf4j
    @Service
    @RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic-2")
    public static class MyConsumer2 implements RocketMQListener<OrderPaidEvent> {
        public void onMessage(OrderPaidEvent orderPaidEvent) {
            log.info("received orderPaidEvent: {}", orderPaidEvent);
        }
    }

    /**
     * 为了测试 相同主题是否都会接受到信息
     * <p>
     * 答案:可以的;
     */
    @Slf4j
    @Service
    @RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic-cp1")
    public static class MyConsumerCP1 implements RocketMQListener<String> {
        public void onMessage(String message) {
            log.info("copy-1 received message: {}", message);
        }
    }

    @Slf4j
    @Service
    @RocketMQMessageListener(topic = "stringRequestTopic", consumerGroup = "stringRequestConsumer")
    public static class StringConsumerWithReplyString implements RocketMQReplyListener<String, String> {
        @Override
        public String onMessage(String message) {
            log.info("StringConsumerWithReplyString received {}", message);
            return "reply string " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        }
    }

    @Slf4j
    @Service
    @RocketMQMessageListener(topic = "objectRequestTopic", consumerGroup = "objectRequestConsumer")
    public static class ObjectConsumerWithReplyUser implements RocketMQReplyListener<OrderPaidEvent, OrderPaidEvent> {
        public OrderPaidEvent onMessage(OrderPaidEvent event) {

            log.info("ObjectConsumerWithReplyUser received {}", event);

            OrderPaidEvent orderPaidEvent = OrderPaidEvent.builder()
                    .orderId("replayOrder")
                    .paidMoney(BigDecimal.valueOf(66.6))
                    .localDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                    .build();

            return orderPaidEvent;
        }
    }

}
