package indi.yufr.redis.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * @date: 2021/5/21 9:20
 * @author: farui.yu
 */
@Data
@RedisHash("person")
public class PersonEntity {

    @Id
    private String id;
    private String name;
    private Address address;

    public PersonEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
