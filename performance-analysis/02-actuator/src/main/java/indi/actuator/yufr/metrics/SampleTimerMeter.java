package indi.actuator.yufr.metrics;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @date: 2020/12/29 11:23
 * @author: farui.yu
 */
@Slf4j
@Component
public class SampleTimerMeter implements InitializingBean {

    private MeterRegistry registry;

    private static Tags tags = Tags.of("region", "timer");

    public SampleTimerMeter(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        timer();
        timerSample();
        timerFunction();
    }

    public void timer() {
        Timer timer = Timer
                .builder("timer.simple")
                .description("a description of what this timer does") // optional
                .tags(tags)
                .register(registry);

        // record, recordCallable
        // wrap: () -> record(runnable)
        timer.record(() -> {
            log.info("hello i'm a timer");
        });
    }

    public void timerSample() {
        Timer.Sample sample = Timer.start(registry);

        String response = getTimedResponse();

        sample.stop(registry.timer("timer.sample", Tags.of(Tag.of("region", "timer"), Tag.of("response", response))));
    }

    @Timed(value = "timer.timed")
    public String getTimedResponse() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
        return "response_wait_500";
    }


    public void timerFunction() {
        Timer timer = Timer.builder("timer.watch")
                .publishPercentileHistogram()
                .publishPercentiles(0.5, 0.95, 0.99)
                .sla(Duration.ofMillis(1000))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(registry);

        registry.more().timer("timer.function", Tags.of("region", "timer"), timer,
                Timer::count,
                t -> t.totalTime(TimeUnit.SECONDS),
                TimeUnit.SECONDS
        );

        timer.record(this::getTimedResponse);
    }

}
