package com.sgai.vbp.web.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sgai.vbp.web.interceptors.UserInfoInterceptor;

@Configuration
public class WebMvcAutoConfiguration implements WebMvcConfigurer {
	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserInfoInterceptor()).addPathPatterns("/**");
	}

	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/").setViewName("forward:/index.html");
        
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    } 
}
