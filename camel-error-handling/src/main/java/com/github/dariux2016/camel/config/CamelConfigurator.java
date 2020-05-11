package com.github.dariux2016.camel.config;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfigurator {
	
	@Bean
	public DefaultErrorHandlerBuilder myDefaultErrorHandler() {
		DefaultErrorHandlerBuilder defaultErrorHandlerBuilder = new DefaultErrorHandlerBuilder();
		defaultErrorHandlerBuilder.maximumRedeliveries(3)
			.maximumRedeliveryDelay(3000)
			.retryAttemptedLogLevel(LoggingLevel.WARN);
		
		return defaultErrorHandlerBuilder;
	}
}