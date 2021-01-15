package indi.actuator.yufr.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @date: 2020/12/25 9:39
 * @author: farui.yu
 */
@Slf4j
@Component
public class SampleCountMeter {

    @Autowired
    private MeterRegistry registry;

    private final AtomicInteger functionCount = new AtomicInteger();
    private final AtomicInteger fluentFunctionCount = new AtomicInteger();
    private Disposable subscribe;

    public Counter counter() {
        return registry.counter("counter.simple", "region", "counter");
    }

    public Counter fluentCounter() {
        return Counter
                .builder("counter.fluent")
                .baseUnit("beans")
                .description("sample fluent counter")
                .tags("region", "counter")
                .register(registry);
    }

    public void functionCounter() {

        registry.more().counter("counter.function", Tags.of("region", "counter"),
                functionCount);
    }

    public void functionFluentCounter() {

        FunctionCounter counter = FunctionCounter
                .builder("counter.function.fluent", fluentFunctionCount, AtomicInteger::get)
                .baseUnit("beans")
                .description("function fluent counter")
                .tags("region", "counter")
                .register(registry);
    }

    public void start() {
        Counter counter = counter();
        Counter fluentCounter = fluentCounter();
        functionCounter();
        functionFluentCounter();

        subscribe = Flux.interval(Duration.ofMillis(5000))
                .doOnEach(d -> {
                    counter.increment();
                    fluentCounter.increment();
                    functionCount.getAndIncrement();
                    fluentFunctionCount.getAndIncrement();
                })
                .subscribe();
    }

    public void dispose() {
        subscribe.dispose();
    }

}
