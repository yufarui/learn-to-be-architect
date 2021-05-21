package indi.yufr.redis.config;

import io.lettuce.core.ReadFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.time.Duration;

/**
 * @date: 2021/5/12 16:16
 * @author: farui.yu
 */
@Configuration
@EnableTransactionManagement
@EnableRedisRepositories(redisTemplateRef="stringRedisTemplate")
public class RedisConfig {

    @Autowired
    private RedisClusterProperties redisClusterProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .useSsl().and()
                // 从slave读,master写
                .readFrom(ReadFrom.REPLICA)
                .commandTimeout(Duration.ofSeconds(2))
                .shutdownTimeout(Duration.ZERO)
                .build();


        // 集群配置 不支持 clientConfig
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(new RedisClusterConfiguration(redisClusterProperties.getNodes()));

        // 对应文档 10.13. Pipelining
        // lettuceConnectionFactory.setPipeliningFlushPolicy(LettuceConnection.PipeliningFlushPolicy.buffered(3));
        // lettuceConnectionFactory.setConvertPipelineAndTxResults(false);

        return lettuceConnectionFactory;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory());
        // explicitly enable transaction support
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
