package indi.biz.yufr.codegen;

import com.baomidou.mybatisplus.generator.InjectionConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2021/1/5 12:36
 * @author: farui.yu
 */
public class SimpleInjectionConfig extends InjectionConfig {

    @Override
    public void initMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("base", CodeConstant.BASE_PACKAGE);
        super.setMap(map);
    }
}
