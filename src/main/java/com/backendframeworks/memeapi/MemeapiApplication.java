package com.backendframeworks.memeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Meme API", version = "1", description = "API for posting memes and following pages!"))
public class MemeapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemeapiApplication.class, args);
	}

}
