package indi.yufr.redis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date: 2021/5/14 11:04
 * @author: farui.yu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String id;
    private String name;

    @JsonProperty("nick_name")
    private String nickName;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
