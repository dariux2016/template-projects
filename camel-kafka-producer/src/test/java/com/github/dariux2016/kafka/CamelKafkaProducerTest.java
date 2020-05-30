package com.github.dariux2016.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.dariux2016.kafka.Starter;
import com.github.dariux2016.kafka.model.Item;
import com.github.dariux2016.kafka.service.ProducerService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Starter.class, initializers = ConfigFileApplicationContextInitializer.class)
public class CamelKafkaProducerTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CamelKafkaProducerTest.class);
	
	@Value("${kafka-uri-base}")
	private String kafkaBaseUri;
	
	@Value("${kafkaProducerConsumerProperties}")
	private String kafkaProperties;
	
	@Autowired
	private ProducerService producerService;
	
	@Test
	public void test() throws Exception {
		String kafkaEndpoint = kafkaBaseUri + kafkaProperties;
		
		LOGGER.info("Test publishing a String");
		producerService.sendBody(kafkaEndpoint, "hello world");
		
		LOGGER.info("Test publishing an Item");
		Item item = Item.builder().code("A").name("first item").build();
		producerService.sendBody(kafkaEndpoint, item);

		//mantain the camel context up and running
		Thread.sleep(5000);
	}
}
