package indi.yuf.rocketmq.service;

import indi.yuf.rocketmq.model.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalRequestCallback;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!"
                + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }

    public void send() {
        //send spring message
        Message<OrderPaidEvent> event = MessageBuilder.withPayload(OrderPaidEvent.builder()
                .orderId("T_002")
                .paidMoney(BigDecimal.valueOf(22.2))
                .localDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build())
                .build();

        rocketMQTemplate.send("test-topic-2", event);
    }

    public void asyncSend() {
        //send message asynchronously
        OrderPaidEvent orderPaidEvent = OrderPaidEvent.builder()
                .orderId("T_003")
                .paidMoney(BigDecimal.valueOf(33.3))
                .localDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        rocketMQTemplate.asyncSend("test-topic-3", orderPaidEvent,
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

    public void transSend() {

        Message<OrderPaidEvent> event = MessageBuilder.withPayload(OrderPaidEvent.builder()
                .orderId("T_004")
                .paidMoney(BigDecimal.valueOf(44.4))
                .localDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build())
                .build();

        rocketMQTemplate.sendMessageInTransaction("test-topic-2", event, "order");

        // 若考虑为分布式事物
        // a.向 ${service}(如订单) 服务发起半事物消息
        // b.向 ${service}(如积分) 服务发起半事物消息
        // 在transactionMQListener中真正执行事物,并提交事物状态

    }

    /**
     * 同步发送
     */
    public void requestString() {
        String replyString = rocketMQTemplate.sendAndReceive("stringRequestTopic",
                "request String " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), String.class);
        log.info(replyString);
    }

    /**
     * 异步发送
     */
    public void requestObject() {

        OrderPaidEvent orderPaidEvent = OrderPaidEvent.builder()
                .orderId("objectRequestTopic")
                .paidMoney(BigDecimal.valueOf(55.5))
                .localDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        rocketMQTemplate.sendAndReceive("objectRequestTopic", orderPaidEvent,
                new RocketMQLocalRequestCallback<OrderPaidEvent>() {
                    @Override
                    public void onSuccess(OrderPaidEvent message) {
                        log.info("send order object and receive {}", message);
                    }

                    @Override
                    public void onException(Throwable e) {
                        e.printStackTrace();
                    }
                }, 5000);

    }

}
