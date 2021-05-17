package indi.yufr.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @date: 2021/5/17 15:57
 * @author: farui.yu
 */

@Component
public class RedisStreamConsuming {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // xread count 1 streams my-stream 0-0
    public void xread() {
        List<MapRecord<String, Object, Object>> read = stringRedisTemplate.opsForStream()
                .read(StreamReadOptions.empty().count(1), StreamOffset.fromStart("my-stream"));

        for (int i = 0; i < read.size(); i++) {
            System.out.println(read.get(i));
        }
    }

    /**
     * 首先 得执行
     * xgroup create my-stream my-group $
     *
     * 等价命令
     * xreadgroup group my-group my-consumer count 1 streams my-stream >
     */
    public void xreadGroup() {
        List<MapRecord<String, Object, Object>> read = stringRedisTemplate.opsForStream()
                .read(Consumer.from("my-group", "my-consumer"),
                        StreamReadOptions.empty().count(1),
                        StreamOffset.create("my-stream", ReadOffset.lastConsumed()));

        for (int i = 0; i < read.size(); i++) {
            System.out.println(read.get(i));
        }
    }
}
