package indi.yufr.redis.model;

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
    String id;
    String name;
}
