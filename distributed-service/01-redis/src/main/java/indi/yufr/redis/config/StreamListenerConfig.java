package indi.yufr.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;

/**
 * @date: 2021/5/17 17:11
 * @author: farui.yu
 * 参考文章
 * https://my.oschina.net/feistel/blog/4682825
 */
@Configuration
public class StreamListenerConfig {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public StreamMessageListenerContainer<String, MapRecord<String, String, String>> container(RedisConnectionFactory connectionFactory) {

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> containerOptions =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                        .pollTimeout(Duration.ofMillis(100))
                        .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container =
                StreamMessageListenerContainer.create(connectionFactory, containerOptions);

        container.start();

        // xreadgroup 消费时 触发此streamListener
        StreamListener<String, MapRecord<String, String, String>> streamListener = message -> {

            System.out.println("MessageId: " + message.getId());
            System.out.println("Stream: " + message.getStream());
            System.out.println("Body: " + message.getValue());
            // body is a linkedHashMap
            stringRedisTemplate.opsForStream().acknowledge("my-group", message);
        };

        container.receive(Consumer.from("my-group", "my-consumer"),
                StreamOffset.create("my-stream", ReadOffset.lastConsumed()),
                streamListener);

        return container;
    }
}
