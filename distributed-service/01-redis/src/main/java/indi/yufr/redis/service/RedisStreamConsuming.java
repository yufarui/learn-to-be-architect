package indi.yufr.redis.service;

import indi.yufr.redis.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
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

    // xread streams my-stream 0-0
    public void xread2() {
        List<ObjectRecord<String, Person>> read = stringRedisTemplate
                .opsForStream(new Jackson2HashMapper(true))
                .read(Person.class, StreamOffset.fromStart("my-stream"));

        for (int i = 0; i < read.size(); i++) {
            System.out.println(read.get(i).getValue().getId());
            System.out.println(read.get(i).getValue().getName());
        }
    }

    /**
     * 首先 得执行
     * xgroup create my-stream simple-redis $
     *
     * 等价命令
     * xreadgroup group simple-redis my-consumer count 1 streams my-stream >
     */
    public void xreadGroup() {
        List<MapRecord<String, Object, Object>> read = stringRedisTemplate.opsForStream()
                .read(Consumer.from("simple-redis", "my-consumer"),
                        StreamReadOptions.empty().count(1),
                        StreamOffset.create("my-stream", ReadOffset.lastConsumed()));

        for (int i = 0; i < read.size(); i++) {
            System.out.println(read.get(i));
        }
    }

    // xreadgroup group simple-redis my-consumer count 1 streams my-stream >
    public void xreadGroup2() {
        List<ObjectRecord<String, Person>> read = stringRedisTemplate.opsForStream(new Jackson2HashMapper(true))
                .read(Person.class, Consumer.from("simple-redis", "my-consumer"),
                        StreamReadOptions.empty().count(1),
                        StreamOffset.create("my-stream", ReadOffset.lastConsumed()));

        for (int i = 0; i < read.size(); i++) {
            System.out.println(read.get(i).getValue().getId());
            System.out.println(read.get(i).getValue().getName());
        }
    }
}
