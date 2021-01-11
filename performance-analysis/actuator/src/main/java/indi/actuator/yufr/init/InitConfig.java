package indi.actuator.yufr.init;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @date: 2020/12/25 8:56
 * @author: farui.yu
 */
@Profile("init")
@ComponentScan("indi.actuator.yufr.init")
@Configuration
public class InitConfig {
}
