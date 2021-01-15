package indi.actuator.yufr.init.endpoint;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2020/12/22 16:22
 * @author: farui.yu
 */
@Component
@RestControllerEndpoint(id = "mvc")
public class SimpleMVCEndpoint {

    @GetMapping
    public Map<String, String> result() {
        Map<String, String> results = new HashMap<>();
        results.put("name", "mvc-endpoint");
        return results;
    }
}
