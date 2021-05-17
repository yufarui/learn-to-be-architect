package indi.yufr.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @date: 2021/5/14 15:21
 * @author: farui.yu
 */
@Configuration
public class PubSubConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("foo"));

        return container;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(RedisSimpleListener listen) {
        //指定监听者和监听方法
        return new MessageListenerAdapter(listen, "handleMsg");
    }

    @Bean
    public KeyExpirationEventMessageListener keyExpirationEventMessageListener
            (RedisMessageListenerContainer container) {
        return new KeyExpirationEventMessageListener(container);
    }
}
