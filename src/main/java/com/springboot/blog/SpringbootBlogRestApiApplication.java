package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App REST APIs",
				description = "Spring Boot Blog App REST APIs Documentation",
				version ="v1.0",
				contact = @Contact(
						name ="Prasad Dharmadhikari",
						email = "prasad.dharmadhikari4669@gmail.com",
						url = "https://github.com/prasad-dharmadhikari/springboot-blog-rest-api"
				),
				license = @License(
						name = "Apache License, Version 2.0",
						url = "https://github.com/prasad-dharmadhikari/springboot-blog-rest-api"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog application documentation",
				url = "https://github.com/prasad-dharmadhikari/springboot-blog-rest-api"
		)
)
public class SpringbootBlogRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}
}
