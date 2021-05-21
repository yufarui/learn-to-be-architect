package indi.yufr.redis.service;

import indi.yufr.redis.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date: 2021/5/17 15:31
 * @author: farui.yu
 */
@Component
public class RedisStreamAppending {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // xadd my-stream * name yufr
    public void xadd(String key, String value) {

        Map<String, String> map = new HashMap<>();
        map.put(key, value);

        StringRecord record = StreamRecords.string(map).withStreamKey("my-stream");

        stringRedisTemplate.opsForStream().add(record);
    }

    /**
     * xrange my-stream - +
     * 此命令没有触发消费
     */
    public void xrange() {
        List<MapRecord<String, Object, Object>> range = stringRedisTemplate.opsForStream()
                .range("my-stream", Range.unbounded());

        for (int i = 0; i < range.size(); i++) {
            System.out.println(range.get(i));
        }
    }

    public void xadd2(String id, String name) {

        ObjectRecord<String, Person> record = StreamRecords.newRecord()
                .in("my-stream")
                .ofObject(new Person(id, name));

        // XADD my-stream * "_class" "indi.yufr.redis.service.Person" "id" "farui" "name" "yu"
        // stringRedisTemplate.opsForStream().add(record);

        // XADD my-stream * "id" "farui"  "name" "yu" "@class" "indi.yufr.redis.service.Person"
        stringRedisTemplate.opsForStream(new Jackson2HashMapper(true)).add(record);
    }

}
