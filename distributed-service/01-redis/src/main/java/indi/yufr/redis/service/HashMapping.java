package indi.yufr.redis.service;

import indi.yufr.redis.model.Person;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @date: 2021/5/14 11:05
 * @author: farui.yu
 */
@Component
public class HashMapping {

    @Resource(name = "stringRedisTemplate")
    HashOperations<String, String, String> hashOperations;

    BeanUtilsHashMapper<Person> mapper = new BeanUtilsHashMapper<>(Person.class);

    /*
     * 等价命令 hmset "person:1" "firstName" "farui" "lastName" "yu"
     */
    public void writeHash(String key, Person person) {

        Map<String, String> mappedHash = mapper.toHash(person);
        hashOperations.putAll(key, mappedHash);
    }

    /*
     * 等价命令 hmget "person:1" "firstName" "lastName"
     */
    public Person loadHash(String key) {

        Map<String, String> loadedHash = hashOperations.entries(key);
        return mapper.fromHash(loadedHash);
    }
}
