package indi.yufr.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
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
}
