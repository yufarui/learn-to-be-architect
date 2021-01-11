package indi.actuator.yufr.init.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2020/12/22 15:55
 * @author: farui.yu
 */
@Component
@WebEndpoint(id = "simple")
public class SimpleWebEndpoint {

    @ReadOperation
    public Map<String, String> result() {
        Map<String, String> results = new HashMap<>();
        results.put("author.name", "yu");
        return results;
    }

}
