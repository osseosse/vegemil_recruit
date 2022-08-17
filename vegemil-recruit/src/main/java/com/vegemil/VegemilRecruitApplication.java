package com.vegemil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class VegemilRecruitApplication extends SpringBootServletInitializer  {

	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(VegemilRecruitApplication.class);
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(VegemilRecruitApplication.class, args);
	    }
}
