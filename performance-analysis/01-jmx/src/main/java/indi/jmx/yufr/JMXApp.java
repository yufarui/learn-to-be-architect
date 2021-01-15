package indi.jmx.yufr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;

/**
 * @date: 2020/12/22 10:35
 * @author: farui.yu
 */
@EnableMBeanExport
@SpringBootApplication
public class JMXApp {
    public static void main(String[] args) {
        SpringApplication.run(JMXApp.class, args);
    }
}
