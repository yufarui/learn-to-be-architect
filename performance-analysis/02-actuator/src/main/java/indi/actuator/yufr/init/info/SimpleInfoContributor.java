package indi.actuator.yufr.init.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @date: 2020/12/23 15:04
 * @author: farui.yu
 */
@Component
public class SimpleInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("simple",
                Collections.singletonMap("item.name", "actuator.info"));
    }
}
