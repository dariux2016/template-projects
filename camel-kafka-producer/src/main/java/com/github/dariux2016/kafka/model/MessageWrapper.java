package com.github.dariux2016.kafka.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model the message which will be sent to (and will be consumed from) a Kafka topic.
 * Encapsulate a payload which is the real object published to the topic.
 * Wrap some utility and system informations such as the timestamp, the caller module and the message type.
 */
@Getter 
@Setter 
@ToString 
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageWrapper implements Serializable{

	private static final long serialVersionUID = 2121211L;
	
	private long timestamp;
	private String callerModule;
	private MessageType messageType;
	private String payload;
}
