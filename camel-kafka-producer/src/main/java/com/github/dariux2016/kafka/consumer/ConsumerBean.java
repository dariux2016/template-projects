package com.github.dariux2016.kafka.consumer;

import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsumerBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerBean.class);

	/**
	 * Get the input object from the current exchange
	 * @param exchange
	 */
	public void consume(@Body Object body) {
		LOGGER.info("Consuming a message {}", body);
	}
}
