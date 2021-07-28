package indi.yufr.redis.service;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @date: 2021/5/12 17:58
 * @author: farui.yu
 */
@Component
public class ExampleService {

    // inject the template as ListOperations
    @Resource(name = "stringRedisTemplate")
    private ListOperations<String, String> listOps;

    public void addLink(String key, String url) {
        listOps.leftPush(key, url);
    }
}
