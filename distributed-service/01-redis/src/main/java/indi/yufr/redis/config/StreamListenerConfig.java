package indi.yufr.redis.config;

import indi.yufr.redis.service.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.convert.MappingRedisConverter;
import org.springframework.data.redis.core.convert.RedisConverter;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Collections;

/**
 * @date: 2021/5/17 17:11
 * @author: farui.yu
 * 参考文章
 * https://my.oschina.net/feistel/blog/4682825
 * <p>
 * 这篇文章说道了重点
 * https://juejin.cn/post/6844904125822435341
 */
@Configuration
@Slf4j
public class StreamListenerConfig implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public RedisConverter redisConverter(RedisMappingContext mappingContext) {
        mappingContext.setInitialEntitySet(Collections.singleton(Person.class));
        return new MappingRedisConverter(mappingContext);
    }

    @Bean
    public ObjectHashMapper hashMapper(RedisConverter converter) {
        return new ObjectHashMapper(converter);
    }

    @Bean
    public StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        // 创建配置对象
        StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> streamMessageListenerContainerOptions =
                StreamMessageListenerContainerOptions.builder()
                        // 一次性最多拉取多少条消息
                        .batchSize(10)
                        .build();

        // 根据配置对象创建监听容器
        return StreamMessageListenerContainer
                .create(redisConnectionFactory, streamMessageListenerContainerOptions);
    }

    @Bean
    public StreamListener<String, MapRecord<String, String, String>> streamListener(
            StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer,
            RedisTemplate<String, String> redisTemplate, Environment environment) throws UnknownHostException {

        String streamKey = "my-stream";
        String groupId = environment.getRequiredProperty("spring.application.name");
        String consumerName = Inet4Address.getLocalHost().getHostName() + ":" + environment.getProperty("server.port");
        System.out.println("streamKey = " + streamKey + ", groupId = " + groupId + ", consumerName = " + consumerName);

        // 判断 stream 是否初始化，未初始化则进行初始化
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(streamKey))) {
            // 往 stream 发送消息，会自动创建 stream
            RecordId recordId = redisTemplate.opsForStream().add(streamKey, Collections.singletonMap("_up", "up"));

            // 创建 消费者组
            redisTemplate.opsForStream().createGroup(streamKey, groupId);

            // 删除创建
            redisTemplate.opsForStream().delete(streamKey, recordId);
        }

        // 监听器
        StreamListener<String, MapRecord<String, String, String>> listener = message -> message.forEach(System.out::println);

        // 使用监听容器监听消息，并且自动应答
        streamMessageListenerContainer.receiveAutoAck(
                Consumer.from(groupId, consumerName),
                StreamOffset.create(streamKey, ReadOffset.lastConsumed()),
                listener);

        return listener;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        // 启动redis stream 监听
        applicationStartedEvent.getApplicationContext()
                .getBeanProvider(StreamMessageListenerContainer.class)
                .ifAvailable(container -> container.start());
    }

}
