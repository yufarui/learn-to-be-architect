package indi.actuator.yufr.metrics;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2020/12/22 10:53
 * @author: farui.yu
 */
@RestController
public class SampleController {

    @Autowired
    private SampleCountMeter sampleMeterCounter;
    @Autowired
    private SampleWordMetrics sampleWordMetrics;

    @GetMapping("/sample/word")
    public void sampleWords() {
        sampleWordMetrics.addWord();
    }

    @GetMapping("/sample/people")
    @Timed(extraTags = {"region", "us-east-1"})
    @Timed(value = "all.people", longTask = true)
    public List<Person> listPeople() {
        if (Math.random() > 0.5) {
            throw new RuntimeException("sample-mvc-error-request");
        }
        List<Person> people = new ArrayList<>();
        people.add(new Person("1"));
        people.add(new Person("2"));
        return people;
    }

    @Data
    @AllArgsConstructor
    static class Person {
        private String name;
    }
}
