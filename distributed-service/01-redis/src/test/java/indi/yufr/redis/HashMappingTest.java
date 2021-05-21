package indi.yufr.redis;

import indi.yufr.redis.service.HashMapping;
import indi.yufr.redis.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @date: 2021/5/14 11:07
 * @author: farui.yu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApp.class})
public class HashMappingTest {

    @Autowired
    private HashMapping hashMapping;

    @Test
    public void hashMapping() {
        hashMapping.writeHash("person:1", new Person("hash-map-1", "yu"));
        Person person = hashMapping.loadHash("person:1");
        System.out.println(person.getId());
        System.out.println(person.getName());
    }
}
