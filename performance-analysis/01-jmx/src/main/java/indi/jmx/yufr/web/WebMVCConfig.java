package indi.jmx.yufr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 添加mvc 拦截器,验证黑名单通过jmx修改成功
 */
@Configuration
public class WebMVCConfig extends WebMvcConfigurationSupport {

	@Autowired
	private BlacklistInterceptor blacklistInterceptor;

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);

		registry
			.addInterceptor(blacklistInterceptor)
			.addPathPatterns("/**");
	}
}
