package indi.yufr.redis.config;

import io.lettuce.core.ReadFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

/**
 * @date: 2021/5/12 16:16
 * @author: farui.yu
 */
@Configuration
public class RedisConfig {

    @Autowired
    private RedisClusterProperties redisClusterProperties;

    @Bean
    public RedisConnectionFactory connectionFactory() {

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .useSsl().and()
                // 从slave读,master写
                .readFrom(ReadFrom.REPLICA)
                .commandTimeout(Duration.ofSeconds(2))
                .shutdownTimeout(Duration.ZERO)
                .build();

        // 集群配置 不支持 clientConfig
        return new LettuceConnectionFactory(new RedisClusterConfiguration(redisClusterProperties.getNodes()));
    }
}
