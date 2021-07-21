package indi.yufr.redis;

import indi.yufr.redis.model.Person;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.Map;

/**
 * @date: 2021/7/13 8:50
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApp.class})
public class ReactiveRedisTest {

    @Autowired
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    BeanUtilsHashMapper<Person> mapper = new BeanUtilsHashMapper<>(Person.class);

    DecoratingStringHashMapper<Person> hashMapper = new DecoratingStringHashMapper(new Jackson2HashMapper(true));

    @SneakyThrows
    @Test
    public void test() {

        Person person = new Person();
        person.setId("1");
        person.setName("yufr");
        person.setNickName("big");

//        Map<String, String> mappedHash = mapper.toHash(person);

        Map<String, String> map = hashMapper.toHash(person);
        ReactiveHashOperations<String, String, Object> reactiveHashOperations =
                reactiveStringRedisTemplate.opsForHash();
        reactiveHashOperations.putAll("person:1", map)
                .then(reactiveStringRedisTemplate.expire("person:1", Duration.ofSeconds(30)))
                .subscribe();

//        reactiveHashOperations
//                .entries("person:1")
//                .collectMap(Map.Entry::getKey, Map.Entry::getValue)
//                .map(res-> mapper.fromHash(res))
//                .subscribe(personRes -> {
//                    System.out.println(personRes);
//                })
        ;
    }

}
