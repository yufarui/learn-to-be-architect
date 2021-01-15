package indi.actuator.yufr.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @date: 2020/12/25 15:08
 * @author: farui.yu
 */
@Component
public class SampleWordMetrics {

    private final List<String> words = new CopyOnWriteArrayList<>();

    SampleWordMetrics(MeterRegistry registry) {
        registry.gaugeCollectionSize("sample.words", Tags.empty(), this.words);
    }

    public void addWord() {
        words.add("[" + (words.size() + 1) + "]");
    }
}
