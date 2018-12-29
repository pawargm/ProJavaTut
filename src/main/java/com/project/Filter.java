package com.project;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.CORSFilter;

@Configuration 
public class Filter {

	
	@Bean
	public FilterRegistrationBean corsFilterRegistration() {
		FilterRegistrationBean reg = new FilterRegistrationBean(new CORSFilter());
		reg.setName("CORS Filter");
		reg.addUrlPatterns("/*");
		reg.setOrder(1);
		return reg;	
	}
	

	
	
}
