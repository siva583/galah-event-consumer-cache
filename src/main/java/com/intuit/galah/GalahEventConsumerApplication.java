package com.intuit.galah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import com.intuit.galah.listener.PostEventListener;
import com.intuit.galah.listener.UserEventListener;
import com.intuit.redis.service.PostServiceImpl;
import com.intuit.redis.service.UserServiceImpl;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class GalahEventConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(GalahEventConsumerApplication.class, args);
	}

	@Bean
	public UserEventListener userEventListener() {
		return new UserEventListener();
	}
	
	@Bean
	public PostEventListener postEventListener() {
		return new PostEventListener();
	}
	
	@Bean
	public UserServiceImpl userService() {
		return new UserServiceImpl();
	}
	
	@Bean
	public PostServiceImpl postService() {
		return new PostServiceImpl();
	}
	
	
	
}


