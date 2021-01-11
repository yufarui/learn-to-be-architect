package indi.jmx.yufr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

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
