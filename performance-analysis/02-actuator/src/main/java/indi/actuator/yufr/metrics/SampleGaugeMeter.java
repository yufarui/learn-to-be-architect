package indi.actuator.yufr.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @date: 2020/12/29 9:51
 * @author: farui.yu
 */
@Component
public class SampleGaugeMeter {

    private MeterRegistry registry;

    private static Tags tags = Tags.of("region", "gauge");

    private List<String> list2 = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();

    public SampleGaugeMeter(MeterRegistry registry) {
        this.registry = registry;
        gauge();
    }

    public void gauge() {

        // list 在此方法结束后,即被回收;则其统计上会消失或NAN
        List<String> list = new ArrayList<>();

        registry.gauge("gauge.list", tags, list, List::size);
        registry.gaugeCollectionSize("gauge.size", tags, list2);
        registry.gaugeMapSize("gauge.map", tags, map);

        Gauge.builder("gauge.fluent.map", map, Map::size)
                .tags("region", "gauge")
                .register(registry);

        list.add("yu");
        list2.add("farui");
        map.put("name", "yufr");
        map.put("address", "shanghai");

        MultiGauge statuses = MultiGauge.builder("gauge.multi")
                .tag("region", "gauge")
                .description("The number of widgets in various statuses")
                .baseUnit("widgets")
                .register(registry);


        statuses.register(
                map.values().stream()
                        .map(v -> MultiGauge.Row.of(Tags.of("value.name", v), v.length()))
                        .collect(Collectors.toList())
        );


    }
}
