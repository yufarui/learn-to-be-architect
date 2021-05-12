package indi.yufr.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @date: 2021/5/12 16:40
 * @author: farui.yu
 */
@Component
@ConfigurationProperties("spring.redis.cluster")
@Data
public class RedisClusterProperties {

    private List<String> nodes;
}
