package indi.biz.yufr.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @date: 2020/6/4 15:44
 * @author: farui.yu
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static String getProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    public static <T> T getBean(String name, Class<T> type) {
        return applicationContext.getBean(name, type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }
}
