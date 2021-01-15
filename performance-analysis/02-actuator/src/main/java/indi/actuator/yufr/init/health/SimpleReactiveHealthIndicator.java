package indi.actuator.yufr.init.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @date: 2020/12/23 11:32
 * @author: farui.yu
 */
@Component
public class SimpleReactiveHealthIndicator implements ReactiveHealthIndicator {

    @Override
    public Mono<Health> health() {
        return doHealthCheck() //perform some specific health check that returns a Mono<Health>
                .onErrorResume(ex -> Mono.just(new Health.Builder().down(ex).build()));
    }

    private Mono<Health> doHealthCheck() {
        return Mono.create(healthMonoSink -> healthMonoSink.error(new RuntimeException("服务错误")));
    }

}
