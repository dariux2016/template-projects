package com.github.dariux2016.camel.exceptionhandling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Indicates messages of any other types than validation messages.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ApplicationMessage extends BaseMessage{

	private static final long serialVersionUID = 6607453871857657253L;
	
	private String message;
	private String code;
	
}
