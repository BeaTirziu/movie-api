package com.example.moviesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// The main application class.
// A @Configuration class indicates that the class contains bean configurations.
// The @Configuration annotation is included within the @SpringBootApplication.
@SpringBootApplication
public class MoviesApiApplication {

	// The entry point for running the Spring Boot application
	public static void main(String[] args) {
		SpringApplication.run(MoviesApiApplication.class, args);
	}

	// A @Bean method indicated that the method creates a bean.
	// In this case, the @Bean annotation is used to define a RestTemplate bean,
	// which can be autowired into other components, like the service.
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
