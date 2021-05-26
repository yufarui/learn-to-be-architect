package indi.yuf.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 参考文档
 * https://github.com/apache/rocketmq-spring/wiki
 *
 * @date: 2021/5/26 8:40
 * @author: farui.yu
 */
@SpringBootApplication
public class RocketMQApp {

    public static void main(String[] args) {
        SpringApplication.run(RocketMQApp.class, args);
    }
}
