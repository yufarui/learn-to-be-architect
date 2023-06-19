package indi.kafka.yufr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaSendService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send() {
        kafkaTemplate.send("test", 0, "key", "this is a msg");
    }

}
