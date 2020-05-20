package com.github.dariux2016;

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
public class EnumerationAppStarter {
	public static void main(final String[] args) {
		new SpringApplication(EnumerationAppStarter.class).run(args);
	}
	
	@Bean
    public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDateTime.class, Long.class)
				.apiInfo(apiInfo())
				.select()
				.paths(PathSelectors.regex("/item.*"))
				.build()
				;
    }
	
	protected ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Enumeration App")
				.description("Use effectively the enumerations with Spring RestController and JPA")
				.version("1.0").build();
	}
}