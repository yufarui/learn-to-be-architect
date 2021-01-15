package indi.actuator.yufr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @date: 2020/12/22 13:45
 * @author: farui.yu
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(includeFilters = {@Filter(type = FilterType.ANNOTATION, classes = Configuration.class)},
        useDefaultFilters = false)
public class ActuatorApp {
    public static void main(String[] args) {
        SpringApplication.run(ActuatorApp.class, args);
    }
}
