package com.github.dariux2016.camel.exceptionhandling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Indicates messages generated through a validation process on the model as input.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ValidationMessage extends BaseMessage{

	private static final long serialVersionUID = -3552424568249178660L;
	
	private String code;
	private String entity;
	private String field;
	private String message;
	
}
