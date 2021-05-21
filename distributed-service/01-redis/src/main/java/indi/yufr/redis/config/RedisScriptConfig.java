package indi.yufr.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * @date: 2021/5/20 11:01
 * @author: farui.yu
 */
@Configuration
public class RedisScriptConfig {

    @Bean
    public RedisScript<Boolean> checkAndSetScript() {

        Resource resource = new ClassPathResource("META-INF/scripts/checkandset.lua");
        return RedisScript.of(resource, Boolean.class);
    }

}
