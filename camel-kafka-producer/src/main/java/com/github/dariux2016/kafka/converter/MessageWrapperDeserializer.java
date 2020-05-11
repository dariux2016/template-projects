package com.github.dariux2016.kafka.converter;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dariux2016.kafka.model.MessageWrapper;

/**
 * Custom Kafka deserializer for MessageWrapper
 *
 */
public class MessageWrapperDeserializer implements Deserializer<Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageWrapperDeserializer.class);
	
	@Override
	public void close() {	}
	
	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {	}
	
	@Override
	public Object deserialize(String topic, byte[] data) {
		LOGGER.info("Deserializing from topic {}",topic);
		ObjectMapper mapper = new ObjectMapper();
		Object payload = null;
		try {
			MessageWrapper message = mapper.readValue(data, MessageWrapper.class);
			String payloadString = message.getPayload();
			payload = mapper.readValue(payloadString, message.getMessageType().value());
			LOGGER.info("Deserialized payload {}", payload);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return payload;
	}
}