package com.github.dariux2016.product;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class RestTemplateAppStarter {
	public static void main(final String[] args) {
		new SpringApplication(RestTemplateAppStarter.class).run(args);
	}
	
	@Bean
    public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDateTime.class, Long.class)
				.apiInfo(apiInfo())
				.select()
				.paths(PathSelectors.regex("/products.*"))
				.build()
				;
    }
	
	protected ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("RestTemplate App")
				.description("Use Spring RestTemplate class to perform HTTP calls and handle errors.")
				.version("1.0").build();
	}
}