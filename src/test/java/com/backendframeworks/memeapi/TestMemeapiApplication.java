package com.backendframeworks.memeapi;

import org.springframework.boot.SpringApplication;

public class TestMemeapiApplication {

	public static void main(String[] args) {
		SpringApplication.from(MemeapiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
