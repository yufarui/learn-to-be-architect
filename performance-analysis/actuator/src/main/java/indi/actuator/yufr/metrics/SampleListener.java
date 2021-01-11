package indi.actuator.yufr.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @date: 2020/12/29 8:52
 * @author: farui.yu
 */
@Component
public class SampleListener implements ApplicationListener<WebServerInitializedEvent> {

    @Autowired
    private SampleCountMeter sampleCountMeter;

    @Autowired
    private SampleTimerMeter sampleTimerMeter;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {

        sampleTimerMeter.getTimedResponse();

        sampleCountMeter.start();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException ignored) {}

        sampleCountMeter.dispose();
    }
}
