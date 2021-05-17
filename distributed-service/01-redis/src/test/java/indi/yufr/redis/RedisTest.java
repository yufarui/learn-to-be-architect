package indi.yufr.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * https://stackoverflow.com/questions/67503106/redistemplate-vs-stringredistemplate-why-redistemplate-set-command-does-not-wor/67528349#67528349
 *
 * stringRedisTemplate 与 redisTemplate 的不同
 *
 * @date: 2021/5/12 16:44
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApp.class})
public class RedisTest {

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> stringValueOperations;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @Test
    public void test() {
        stringValueOperations.set("name", "yufr-bigName");
        valueOperations.set("name", "yufr");
        System.out.println(stringValueOperations.get("name"));
        System.out.println(valueOperations.get("name"));
    }


    @Test
    public void serialize() {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(out)) {
            objectOutputStream.writeObject("name");
            objectOutputStream.flush();
        } catch (IOException ioException) {

        }
        System.out.println(new String(out.toByteArray()));
    }

}
