package indi.actuator.yufr.init.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @date: 2020/12/23 11:20
 * @author: farui.yu
 */
@Component
public class SimpleHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        if(Math.random() > 0.5) {
            return Health.up().withDetail("msg", "网络连接正常...").build();
        }
        return Health.down().withDetail("msg", "网络断开...").build();
    }
}
