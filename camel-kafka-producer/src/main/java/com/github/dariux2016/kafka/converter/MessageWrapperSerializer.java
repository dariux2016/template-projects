package com.github.dariux2016.kafka.converter;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dariux2016.kafka.model.MessageWrapper;

/**
 * Custom Kafka Serializer for MessageWrapper
 *
 */
public class MessageWrapperSerializer implements Serializer<MessageWrapper> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageWrapperSerializer.class);

	@Override
	public void configure(Map<String, ?> map, boolean b) {	}

	@Override
	public byte[] serialize(String arg0, MessageWrapper arg1) {
		LOGGER.info("Serializing message wrapper {}", arg1);
		byte[] retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsString(arg1).getBytes();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return retVal;
	}

	@Override
	public void close() {	}
}