package indi.kafka.yufr;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "test", groupId = "test_group_0")
    public void listenGroup0(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        log.info("group_0接受到信息[{}]", value);
        //手动提交offset
        ack.acknowledge();
    }

/*    @KafkaListener(topics = "test", groupId = "test_group_1")
    public void listenGroup1(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        log.info("group_0接受到信息[{}]", value);
        ack.acknowledge();
    }*/
}
