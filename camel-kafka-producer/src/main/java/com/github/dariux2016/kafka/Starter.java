package com.github.dariux2016.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Starter {

	public static void main(final String[] args) {
		SpringApplication service=new SpringApplicationBuilder()
					                .sources(Starter.class)
					                .web(WebApplicationType.NONE)
					                .build();
		service.run(args);
	}
	
}
