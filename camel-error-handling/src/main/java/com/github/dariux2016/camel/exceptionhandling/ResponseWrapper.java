package com.github.dariux2016.camel.exceptionhandling;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Wrap the response messages (application and validation) and 
 * the generic object associated to the messages.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ResponseWrapper implements Serializable {
	
	private static final long serialVersionUID = -9176766069696459856L;
	
	private List<ApplicationMessage> applicationMessages;
	private List<ValidationMessage> validationMessages;
	
	private String objectName;
	private Object object;

}
