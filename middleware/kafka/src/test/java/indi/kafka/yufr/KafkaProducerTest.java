package indi.kafka.yufr;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
public class KafkaProducerTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @SneakyThrows
    @Test
    public void sendMsg() {
        kafkaTemplate.send("test", "key", "this is a msg");

        // 等待消费
        Thread.sleep(10000);
    }
}
