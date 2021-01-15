package indi.actuator.yufr.metrics;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.prometheus.PrometheusNamingConvention;
import io.micrometer.prometheus.PrometheusRenameFilter;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.*;

/**
 * @date: 2020/12/25 9:12
 * @author: farui.yu
 */
@Profile("metrics")
@ComponentScan("indi.actuator.yufr.metrics")
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class MetricsConfig {

    @Bean
    @ConditionalOnClass(name = "io.micrometer.prometheus.PrometheusMeterRegistry")
    public MeterRegistryCustomizer<?> sampleMetricsRegistry() {
        return registry -> registry.config()
                .commonTags("region", "sample")
//                .namingConvention(new PrometheusNamingConvention("_simple_duration"))
                .meterFilter(new PrometheusRenameFilter());
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}
